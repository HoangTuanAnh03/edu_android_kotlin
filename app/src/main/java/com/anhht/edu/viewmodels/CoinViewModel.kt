package com.anhht.edu.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Question
import com.anhht.edu.repository.CoinAPIService

class CoinViewModel(val coinRepo : CoinAPIService
) : ViewModel() {
    fun postAnswer(context: Context, answer:String, wid:Int): LiveData<ResponseApi<Int>> {
        return coinRepo.postAnswer(context, answer, wid)
    }

    fun getUserInformation(context: Context): LiveData<ResponseApi<Map<String, String>>>{
        return coinRepo.getUserInformation(context)
    }
}