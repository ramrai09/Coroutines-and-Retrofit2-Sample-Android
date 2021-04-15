package com.onebanc.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onebanc.assignment.model.Transaction
import com.onebanc.assignment.model.TransactionStatus
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ItemSentViewModel(transaction: Transaction) : ViewModel() {

    val dateTimeString = MutableLiveData<String>()
    val amount = MutableLiveData<String>()
    val transactionId = MutableLiveData<String>()
    val isSuccess=MutableLiveData<Boolean>()
    val isPending=MutableLiveData<Boolean>()

    init {
        transaction.dateOfTransaction?.apply {
            val dateTimeFormatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            dateTimeString.value = dateTimeFormatter.format(this)
        }
        val formattedAmount=DecimalFormat("0.####").format(transaction.amount)
        amount.value = "\u20B9 $formattedAmount"
        transactionId.value=transaction.id.toString()
        isSuccess.value=transaction.status== TransactionStatus.CONFIRMED.data
        isPending.value=transaction.status== TransactionStatus.PENDING.data
    }
}