package com.anhht.edu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Question
import com.anhht.edu.repository.WordAPIService

class WordViewModel(val wordRepo : WordAPIService
) : ViewModel() {
    fun getWordByTid(tid:Int): LiveData<List<Question>> {
        return wordRepo.getQuestionByTid(tid)
    }
    fun getWordByTidTest(tid:Int): LiveData<List<Question>> {
        return wordRepo.getQuestionByTidTest(tid)
    }
    fun getTest(): LiveData<ResponseApi<List<Question>>> {
        return wordRepo.getTest()
    }
}