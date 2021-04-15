package com.onebanc.assignment.model

import com.onebanc.assignment.model.Transaction

data class TransactionHistoryResponse(
    val receipeintId: Int,
    val transactions: List<Transaction>,
    val userId: Int
)