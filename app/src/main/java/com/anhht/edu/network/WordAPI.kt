package com.anhht.edu.network

import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Question
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordAPI {
    @GET("words/getQuestionByTid")
    fun getWordsByTid(@Query("tid") tid:Int): Call<ResponseApi<List<Question>>>?

    @GET("words/getQuestionByTidTest")
    fun getWordsByTidTest(@Query("tid") tid:Int): Call<ResponseApi<List<Question>>>?

    @GET("words/getTest")
    fun getTest(): Call<ResponseApi<List<Question>>>?
}