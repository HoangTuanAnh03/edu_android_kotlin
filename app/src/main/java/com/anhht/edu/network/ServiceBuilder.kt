package com.anhht.edu.network

import android.content.Context
import com.anhht.edu.config.AuthInterceptor
import com.anhht.edu.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceBuilder {
    private val client = OkHttpClient.Builder()
        .connectTimeout(400000, TimeUnit.MILLISECONDS)
        .readTimeout(400000, TimeUnit.MILLISECONDS)

    fun <T> buildService(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
        return retrofit.create(service)
    }

    fun <T> buildService(context: Context, service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient(context))
            .build()
        return retrofit.create(service)
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return client
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}