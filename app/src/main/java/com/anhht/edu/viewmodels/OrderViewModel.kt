package com.anhht.edu.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Order
import com.anhht.edu.model.data.OrderHistory
import com.anhht.edu.model.request.OrderRequest
import com.anhht.edu.repository.CoinAPIService
import com.anhht.edu.repository.OrderAPIService

class OrderViewModel(val orderRepo : OrderAPIService
) : ViewModel() {
    fun addNewOrder(order : OrderRequest): LiveData<ResponseApi<Order>>{
        return orderRepo.addNewOrder(order)
    }
    fun getOrderHistory():LiveData<List<OrderHistory>>{
        return orderRepo.getOrderHistory()
    }
}