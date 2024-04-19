package com.anhht.edu

import com.anhht.edu.test.TestApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("users/sign-in")
    fun signIn(@Body userData: UserRequest): Call<ResponseApi>

    @GET("randomusers?page=1&limit=1")
    fun getCourse(): Call<TestApi?>?
}