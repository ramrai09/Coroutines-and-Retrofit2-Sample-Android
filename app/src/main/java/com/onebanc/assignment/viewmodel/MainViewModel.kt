package com.onebanc.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onebanc.assignment.event.SingleLiveEvent
import com.onebanc.assignment.model.Transaction
import com.onebanc.assignment.repository.RemoteRepository

class MainViewModel : ViewModel() {

    val showProgress = MutableLiveData<Boolean>()
    val showToast = SingleLiveEvent<String>()
    val transactions = MutableLiveData<List<Transaction>>()

    init {
        showProgress.value = true
        getData()
    }

     fun getData() {
        RemoteRepository.getInstance().getTransactionHistory({
            showProgress.value = false
            transactions.value = it.transactions
        }, {
            showProgress.value = false
            showToast.value = it.message
        })
    }
}