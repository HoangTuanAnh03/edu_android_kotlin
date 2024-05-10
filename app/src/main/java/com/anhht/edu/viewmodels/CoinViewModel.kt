package com.anhht.edu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Question
import com.anhht.edu.repository.CoinAPIService

class CoinViewModel(val coinRepo : CoinAPIService
) : ViewModel() {
    fun postAnswer(answer:String, wid:Int): LiveData<ResponseApi<Int>> {
        return coinRepo.postAnswer(answer, wid)
    }
}