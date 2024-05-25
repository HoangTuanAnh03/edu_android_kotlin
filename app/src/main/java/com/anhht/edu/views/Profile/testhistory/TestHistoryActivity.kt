package com.anhht.edu.views.Profile.testhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityTestHistoryBinding
import com.anhht.edu.databinding.ActivityTestResultsBinding
import com.anhht.edu.repository.OrderAPIService
import com.anhht.edu.repository.TestHistoryAPIService
import com.anhht.edu.viewmodels.OrderViewModel
import com.anhht.edu.viewmodels.TestHistoryViewModel
import com.anhht.edu.views.Adapter.RVOrderHistoryAdapter
import com.anhht.edu.views.Adapter.RVTestHistoryAdapter

class TestHistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestHistoryBinding
    lateinit var rv : RecyclerView
    lateinit var testApdater: RVTestHistoryAdapter
    lateinit var testViewModel: TestHistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rv = binding.TestHistoryRecycleView
        rv.layoutManager = LinearLayoutManager(this)
        testViewModel = TestHistoryViewModel(TestHistoryAPIService())
        testViewModel.getAllTestHistory().observe(this){data->
            testApdater = RVTestHistoryAdapter(this, data)
            rv.adapter = testApdater
        }
        binding.actionbarTestHistory.setOnClickListener{
            finish()
        }
    }
}