package com.anhht.edu.network

import com.anhht.edu.model.data.Data
import com.anhht.edu.model.request.RegisterRequest
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Register
import com.anhht.edu.model.request.ChangePasswordRequest
import com.anhht.edu.model.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {
    @POST("users/sign-in")
    fun signIn(@Body userData: LoginRequest): Call<ResponseApi<Data>>

    @GET("coin/getCoin")
    fun getAll(): Call<ResponseApi<String>>?
    @GET("users/exitEmail")
    fun exitEmail(@Query("email") email: String?): Call<ResponseApi<String>>

    @POST("users/register")
    fun register(@Body userData: RegisterRequest): Call<ResponseApi<Register>>

    @POST("users/forgetPassword")
    fun forgetPassword(@Query("email") email : String): Call<ResponseApi<String>>
    @POST("users/forgetPassword/checkOtp")
    fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Call<ResponseApi<String>>

    @GET("forgetPassword/checkOtp")
    fun checkOtp(@Query("email") email: String, @Query("otp") otp: String): Call<ResponseApi<String>>

    @GET("users/getUserInformation")
    fun getUserInformation(): Call<ResponseApi<Map<String, String>>>?

}