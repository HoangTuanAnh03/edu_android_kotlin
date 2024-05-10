package com.anhht.edu.network

import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Question
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinAPI {
    @GET("coin/post")
    fun postAnswer(@Query("answer") answer:String, @Query("wid") wid: Int): Call<ResponseApi<Int>>?
}