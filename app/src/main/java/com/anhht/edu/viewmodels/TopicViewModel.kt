package com.anhht.edu.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.data.Topic
import com.anhht.edu.repository.TopicAPIService

class TopicViewModel(val topicRepo : TopicAPIService
) : ViewModel() {
    fun getTopicByLid(context: Context, lid:Int): LiveData<List<Topic>> {
        return topicRepo.getTopicByLid(context, lid)
    }
}