package com.anhht.edu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.data.TopicByLevel
import com.anhht.edu.repository.TopicAPIService

class TopicViewModel(val topicRepo : TopicAPIService
) : ViewModel() {
    fun getTopicByLid(lid:Int): LiveData<TopicByLevel> {
        return topicRepo.getTopicByLid(lid)
    }
}