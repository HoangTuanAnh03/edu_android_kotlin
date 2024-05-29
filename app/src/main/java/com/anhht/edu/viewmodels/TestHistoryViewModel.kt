package com.anhht.edu.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.TestHistory
import com.anhht.edu.repository.CoinAPIService
import com.anhht.edu.repository.TestHistoryAPIService

class TestHistoryViewModel (val historyRepo : TestHistoryAPIService
) : ViewModel() {
    fun addTestHistory(context: Context, numQues : Int, numCorrectQues : Int): LiveData<TestHistory>{
        return historyRepo.addTestHistory(context, numQues, numCorrectQues)
    }

    fun getAllTestHistory(context: Context): LiveData<List<TestHistory>>{
        return historyRepo.getAllTestHistory(context)
    }
}