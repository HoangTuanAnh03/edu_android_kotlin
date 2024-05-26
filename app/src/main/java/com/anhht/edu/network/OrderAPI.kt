package com.anhht.edu.network

import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Order
import com.anhht.edu.model.data.OrderHistory
import com.anhht.edu.model.data.Product
import com.anhht.edu.model.request.OrderRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderAPI {
    @POST("order/add")
    fun addNewOrder(@Body order: OrderRequest): Call<ResponseApi<Order>>?

    @GET("order/getAll")
    fun getOrderHistory(): Call<ResponseApi<List<OrderHistory>>>?
}