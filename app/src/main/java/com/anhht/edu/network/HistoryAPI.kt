package com.anhht.edu.network

import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.TestHistory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HistoryAPI {
    @POST("history/add")
    fun addTestHistory(@Query("numQues") numQues:Int, @Query("numCorrectQues") numCorrectQues: Int): Call<ResponseApi<TestHistory>>?

    @GET("history/getAll")
    fun getAllTestHistory(): Call<ResponseApi<List<TestHistory>>>?
}