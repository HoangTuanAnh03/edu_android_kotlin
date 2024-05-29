package com.anhht.edu.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Question
import com.anhht.edu.repository.WordAPIService

class WordViewModel(val wordRepo : WordAPIService
) : ViewModel() {
    fun getWordByTid(context: Context, tid:Int): LiveData<List<Question>> {
        return wordRepo.getQuestionByTid(context, tid)
    }
    fun getWordByTidTest(context: Context, tid:Int): LiveData<List<Question>> {
        return wordRepo.getQuestionByTidTest(context, tid)
    }
    fun getTest(context: Context): LiveData<ResponseApi<List<Question>>> {
        return wordRepo.getTest(context)
    }
}