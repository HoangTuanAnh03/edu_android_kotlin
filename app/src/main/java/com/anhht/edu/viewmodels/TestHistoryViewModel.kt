package com.anhht.edu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.TestHistory
import com.anhht.edu.repository.CoinAPIService
import com.anhht.edu.repository.TestHistoryAPIService

class TestHistoryViewModel (val historyRepo : TestHistoryAPIService
) : ViewModel() {
    fun addTestHistory(numQues : Int, numCorrectQues : Int): LiveData<TestHistory>{
        return historyRepo.addTestHistory(numQues, numCorrectQues)
    }

    fun getAllTestHistory(): LiveData<List<TestHistory>>{
        return historyRepo.getAllTestHistory()
    }
}