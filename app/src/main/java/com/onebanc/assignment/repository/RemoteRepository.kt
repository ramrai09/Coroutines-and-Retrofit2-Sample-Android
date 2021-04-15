package com.onebanc.assignment.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.onebanc.assignment.api.ApiRequests
import com.onebanc.assignment.api.PostProcessingEnabler
import com.onebanc.assignment.model.TransactionHistoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRepository {

    companion object {
        private var repository: RemoteRepository? = null
        fun getInstance(): RemoteRepository {
            if (repository == null) {
                repository = RemoteRepository()
            }
            return repository!!
        }
    }

    private val BASE_URL = "https://dev.onebanc.ai/assignment.asmx/"
    private val TAG = "RemoteRepository"
    private val api: ApiRequests

    init {
        val gson = GsonBuilder()
            .setLenient()
            .registerTypeAdapterFactory(PostProcessingEnabler())
            .create()
        api =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiRequests::class.java)
    }

    fun getTransactionHistory(
        onSuccess: (TransactionHistoryResponse) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getTransactionHistory(1, 2).awaitResponse()
                Log.d(TAG, response.toString())
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null) {
                        GlobalScope.launch(Dispatchers.Main) { onFailure(Exception("No data from server")) }
                    } else {
                        GlobalScope.launch(Dispatchers.Main) { onSuccess(data) }
                    }
                } else {
                    GlobalScope.launch(Dispatchers.Main) { onFailure(Exception("Request failed")) }
                }
            } catch (exception: Exception) {
                GlobalScope.launch(Dispatchers.Main) { onFailure(exception) }
            }
        }
    }
}