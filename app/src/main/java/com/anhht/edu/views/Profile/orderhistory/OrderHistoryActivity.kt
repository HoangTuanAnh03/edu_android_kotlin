package com.anhht.edu.views.Profile.orderhistory

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityOrderHistoryBinding
import com.anhht.edu.repository.OrderAPIService
import com.anhht.edu.viewmodels.OrderViewModel
import com.anhht.edu.viewmodels.TopicViewModel
import com.anhht.edu.views.Adapter.RVOrderHistoryAdapter
import com.anhht.edu.views.Adapter.RVTopicAdapter

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    lateinit var rvMain: RecyclerView
    lateinit var orderApdater: RVOrderHistoryAdapter
    lateinit var orderViewModel: OrderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
    }
    override fun onResume() {
        super.onResume()
        setContentView(binding.root)
        Log.e("context1", this@OrderHistoryActivity.toString())
        rvMain = binding.OrderHistoryRecycleView
        rvMain.layoutManager = LinearLayoutManager(this)
        orderViewModel = OrderViewModel(OrderAPIService())
        orderViewModel.getOrderHistory(this@OrderHistoryActivity).observe(this@OrderHistoryActivity){data->
            orderApdater = RVOrderHistoryAdapter(this@OrderHistoryActivity, data)
            rvMain.adapter = orderApdater
        }
        Toast.makeText(this, "1231231312", Toast.LENGTH_SHORT).show()
        binding.orderHistoryBack.setOnClickListener{
            finish()
        }
    }
}