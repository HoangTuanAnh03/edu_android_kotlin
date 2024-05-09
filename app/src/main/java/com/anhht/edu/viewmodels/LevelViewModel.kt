package com.anhht.edu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.data.Level
import com.anhht.edu.repository.LevelAPIService

class LevelViewModel(
    val levelRepo : LevelAPIService
) : ViewModel(){
    fun getAllUsers(): LiveData<List<Level>> {
        return levelRepo.getAllLevel()
    }
}