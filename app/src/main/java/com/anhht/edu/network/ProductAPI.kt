package com.anhht.edu.network

import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Level
import com.anhht.edu.model.data.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductAPI {
    @GET("product/getAll")
    fun getAll(): Call<ResponseApi<List<Product>>>?
}