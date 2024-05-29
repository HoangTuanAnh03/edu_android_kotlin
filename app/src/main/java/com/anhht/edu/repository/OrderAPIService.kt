package com.anhht.edu.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Order
import com.anhht.edu.model.data.OrderHistory
import com.anhht.edu.model.data.Product
import com.anhht.edu.model.request.OrderRequest
import com.anhht.edu.network.CoinAPI
import com.anhht.edu.network.OrderAPI
import com.anhht.edu.network.ProductAPI
import com.anhht.edu.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderAPIService {
    private val responseOrder: MutableLiveData<ResponseApi<Order>> = MutableLiveData<ResponseApi<Order>>()
    private val orderHistory: MutableLiveData<List<OrderHistory>> = MutableLiveData<List<OrderHistory>>()
    fun addNewOrder(context: Context, order: OrderRequest) : MutableLiveData<ResponseApi<Order>>{
        val retrofit = ServiceBuilder.buildService(OrderAPI::class.java)
        retrofit.addNewOrder(order)!!.enqueue(
            object : Callback<ResponseApi<Order>> {
                override fun onResponse(
                    call: Call<ResponseApi<Order>>,
                    response: Response<ResponseApi<Order>>
                ) {
                    val data = response.body()
                    Log.e("data",data.toString())
                    responseOrder.value = data!!
                }
                override fun onFailure(call: Call<ResponseApi<Order>>, t: Throwable) {
                    Log.e("CoinAPI", t.message.toString())

                }
            }
        )
        return responseOrder
    }

    fun getOrderHistory(context: Context) : MutableLiveData<List<OrderHistory>>{
        val retrofit = ServiceBuilder.buildService(OrderAPI::class.java)
        retrofit.getOrderHistory()!!.enqueue(
            object : Callback<ResponseApi<List<OrderHistory>>> {
                override fun onResponse(
                    call: Call<ResponseApi<List<OrderHistory>>>,
                    response: Response<ResponseApi<List<OrderHistory>>>
                ) {
                    val data = response.body()
                    Log.e("history data", data.toString())
                    orderHistory.value = data!!.data
                }
                override fun onFailure(call: Call<ResponseApi<List<OrderHistory>>>, t: Throwable) {

                }
            }
        )
        return orderHistory
    }
}