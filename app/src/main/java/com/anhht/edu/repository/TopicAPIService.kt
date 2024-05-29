package com.anhht.edu.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.TopicByLevel
import com.anhht.edu.network.ServiceBuilder
import com.anhht.edu.network.TopicAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicAPIService {
    private lateinit var topics: TopicByLevel
    private val topicsByLevel: MutableLiveData<TopicByLevel> = MutableLiveData<TopicByLevel>()
    fun getTopicByLid(context: Context, lid:Int) : MutableLiveData<TopicByLevel> {
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
                        topics = data.data
                        topicsByLevel.value = topics
                    }
                }
                override fun onFailure(call: Call<ResponseApi<TopicByLevel>>, t: Throwable) {
//                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("TopicByLevel", t.message.toString())
                }
            }
        )
        return topicsByLevel
    }
}