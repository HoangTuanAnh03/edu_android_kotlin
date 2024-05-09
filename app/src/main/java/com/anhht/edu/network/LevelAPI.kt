package com.anhht.edu.network

import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Level
import retrofit2.Call
import retrofit2.http.GET

interface LevelAPI {
    @GET("level/getAll")
    fun getAll(): Call<ResponseApi<List<Level>>>?
}