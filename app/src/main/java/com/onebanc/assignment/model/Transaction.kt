package com.onebanc.assignment.model

import com.onebanc.assignment.api.PostProcessable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Transaction(
    val amount: Double,
    val customer: Customer,
    val description: String,
    val direction: Int,
    val endDate: String,
    val id: Int,
    val partner: Partner,
    val startDate: String,
    val status: Int,
    val type: Int
) : PostProcessable {

    var dateOfTransaction: Date? = null

    override fun gsonPostProcess() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        dateOfTransaction = try {
            dateFormat.parse(startDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }
}