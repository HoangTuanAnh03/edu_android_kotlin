package com.anhht.edu.views.Profile.orderhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        setContentView(binding.root)

        rvMain = binding.OrderHistoryRecycleView
        rvMain.layoutManager = LinearLayoutManager(this)

        orderViewModel = OrderViewModel(OrderAPIService())
        orderViewModel.getOrderHistory().observe(this){data->
            orderApdater = RVOrderHistoryAdapter(this, data)
            rvMain.adapter = orderApdater
        }
        binding.orderHistoryBack.setOnClickListener{
            finish()
        }
    }
}