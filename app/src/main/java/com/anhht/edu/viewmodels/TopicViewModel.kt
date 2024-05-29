package com.anhht.edu.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.data.TopicByLevel
import com.anhht.edu.repository.TopicAPIService

class TopicViewModel(val topicRepo : TopicAPIService
) : ViewModel() {
    fun getTopicByLid(context: Context, lid:Int): LiveData<TopicByLevel> {
        return topicRepo.getTopicByLid(context, lid)
    }
}