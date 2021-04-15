package com.onebanc.assignment.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onebanc.assignment.model.Transaction
import com.onebanc.assignment.databinding.*
import com.onebanc.assignment.viewmodel.ItemReceivedViewModel
import com.onebanc.assignment.viewmodel.ItemSentViewModel
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val transactions = mutableListOf<Transaction>()
    private val dataList = mutableListOf<Any>()

    private enum class ItemViewType(val data: Int) {
        VIEW_TYPE_DATE(1),
        VIEW_TYPE_SENT(2),
        VIEW_TYPE_RECEIVED(3)
    }

    private enum class TransactionDirection(val data: Int) {
        SENT(1),
        RECEIVED(2)
    }

    fun addAll(transactions: List<Transaction>) {
        this.transactions.addAll(transactions)
        notifyTransactionsListChanged()
    }

    fun clear() {
        transactions.clear()
        dataList.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ItemViewType.VIEW_TYPE_SENT.data -> SentViewHolder(
                ItemSentBinding.inflate(
                    layoutInflater
                )
            )
            ItemViewType.VIEW_TYPE_RECEIVED.data -> ReceivedViewHolder(
                ItemReceivedBinding.inflate(
                    layoutInflater
                )
            )
            else -> DateViewHolder(ItemDateBinding.inflate(layoutInflater))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SentViewHolder -> {
                holder.itemDate.viewModel = ItemSentViewModel((getItem(position) as Transaction))
            }
            is ReceivedViewHolder -> {
                holder.itemDate.viewModel = ItemReceivedViewModel((getItem(position) as Transaction))
            }
            is DateViewHolder -> {
                holder.itemDate.date = getFormattedDataString((getItem(position) as Date))
            }
        }
    }

    private fun getFormattedDataString(date: Date):String{
        val  simpleDateFormat =  SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemViewType(position: Int): Int {
        when (val item = getItem(position)) {
            is Date -> {
                return ItemViewType.VIEW_TYPE_DATE.data
            }
            is Transaction -> {
                return when (item.direction) {
                    TransactionDirection.SENT.data -> {
                        ItemViewType.VIEW_TYPE_SENT.data
                    }
                    TransactionDirection.RECEIVED.data -> {
                        ItemViewType.VIEW_TYPE_RECEIVED.data
                    }
                    else -> {
                        super.getItemViewType(position)
                    }
                }
            }
            else -> {
                return super.getItemViewType(position)
            }
        }
    }

    private fun notifyTransactionsListChanged() {
        dataList.clear()
        transactions.sortBy { it.dateOfTransaction }
        val dateOnlyDateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val groupedTransactions = transactions.groupBy {
            dateOnlyDateFormatter.parse(dateOnlyDateFormatter.format(it.dateOfTransaction))
        }
        for (key in groupedTransactions.keys) {
            dataList.add(key!!)
            dataList.addAll(groupedTransactions[key]!!.toList())
        }
    }

    private class DateViewHolder(val itemDate: ItemDateBinding) :
        RecyclerView.ViewHolder(itemDate.root)

    private class SentViewHolder(val itemDate: ItemSentBinding) :
        RecyclerView.ViewHolder(itemDate.root)

    private class ReceivedViewHolder(val itemDate: ItemReceivedBinding) :
        RecyclerView.ViewHolder(itemDate.root)
}