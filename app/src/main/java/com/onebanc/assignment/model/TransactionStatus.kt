package com.onebanc.assignment.model

 enum class TransactionStatus(val data: Int) {
        PENDING(1),
        CONFIRMED(2),
        EXPIRED(3),
        REJECT(4),
        CANCEL(5)
    }