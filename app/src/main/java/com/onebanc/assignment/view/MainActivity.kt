package com.onebanc.assignment.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onebanc.assignment.R
import com.onebanc.assignment.databinding.ActivityMainBinding
import com.onebanc.assignment.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.lifecycleOwner = this
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        val viewModel = MainViewModel()
        viewBinding.viewModel = viewModel

        val transactionAdapter = TransactionAdapter()
        viewBinding.recyclerView.adapter = transactionAdapter

        viewModel.showToast.observe(this, Observer {
            showToast(it)
        })
        viewModel.transactions.observe(this, Observer {
            transactionAdapter.clear()
            transactionAdapter.addAll(it)
            transactionAdapter.notifyDataSetChanged()
        })

        viewBinding.pullToRefresh.setOnRefreshListener {
            viewModel.getData()
        }

        viewModel.showProgress.observe(this, Observer {
            if (!it) {
                viewBinding.pullToRefresh.isRefreshing = false
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }
}