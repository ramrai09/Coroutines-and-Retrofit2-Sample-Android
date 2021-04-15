package com.onebanc.assignment.api

import com.onebanc.assignment.model.TransactionHistoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {

    @GET("GetTransactionHistory")
    fun getTransactionHistory(
        @Query("userId") userId: Int,
        @Query("recipientId") recipientId: Int
    ): Call<TransactionHistoryResponse>
}