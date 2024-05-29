package com.anhht.edu.views.profile.testhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anhht.edu.databinding.ActivityTestHistoryBinding
import com.anhht.edu.repository.TestHistoryAPIService
import com.anhht.edu.viewmodels.TestHistoryViewModel
import com.anhht.edu.adapter.RVTestHistoryAdapter

class TestHistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestHistoryBinding
    private lateinit var rv : RecyclerView
    private lateinit var testAdapter: RVTestHistoryAdapter
    private lateinit var testViewModel: TestHistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rv = binding.TestHistoryRecycleView
        rv.layoutManager = LinearLayoutManager(this)
        testViewModel = TestHistoryViewModel(TestHistoryAPIService())
        testViewModel.getAllTestHistory(this@TestHistoryActivity).observe(this){data->
            testAdapter = RVTestHistoryAdapter(this, data)
            rv.adapter = testAdapter
        }
        binding.actionbarTestHistory.setOnClickListener{
            finish()
        }
    }
}