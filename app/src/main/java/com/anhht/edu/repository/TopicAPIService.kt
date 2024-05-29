package com.anhht.edu.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Topic
import com.anhht.edu.model.data.TopicByLevel
import com.anhht.edu.network.ServiceBuilder
import com.anhht.edu.network.TopicAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicAPIService {
    private lateinit var topics: List<Topic>
    private val topicsByLevel: MutableLiveData<List<Topic>> = MutableLiveData<List<Topic>>()
    fun getTopicByLid(context: Context, lid:Int) : MutableLiveData<List<Topic>> {
        val retrofit = ServiceBuilder.buildService(TopicAPI::class.java)
        retrofit.getTopicByLid(lid)!!.enqueue(
            object : Callback<ResponseApi<TopicByLevel>> {
                override fun onResponse(
                    call: Call<ResponseApi<TopicByLevel>>,
                    response: Response<ResponseApi<TopicByLevel>>
                ) {
                    val data = response.body()
                    Log.e("data",data.toString())
                    if(data?.data?.listTopics != null){
                        topics = data.data.listTopics
                        topicsByLevel.value = topics
                    }
                }
                override fun onFailure(call: Call<ResponseApi<TopicByLevel>>, t: Throwable) {
                    Log.e("TopicByLevel", t.message.toString())
                }
            }
        )
        return topicsByLevel
    }
}