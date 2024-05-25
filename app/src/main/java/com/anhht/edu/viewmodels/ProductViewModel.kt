package com.anhht.edu.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anhht.edu.model.data.Level
import com.anhht.edu.model.data.Product
import com.anhht.edu.repository.LevelAPIService
import com.anhht.edu.repository.ProductAPIService

class ProductViewModel(val productRepo : ProductAPIService):ViewModel() {
    fun getAllProducts(context : Context): LiveData<List<Product>> {
        return productRepo.getAllProducts(context)
    }
}