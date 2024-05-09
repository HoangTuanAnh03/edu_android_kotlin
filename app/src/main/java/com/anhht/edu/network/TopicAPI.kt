package com.anhht.edu.network

import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.TopicByLevel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TopicAPI {
    @GET("topic/getTopicByLid")
    fun getTopicByLid(@Query("lid") lid:Int): Call<ResponseApi<TopicByLevel>>?
}