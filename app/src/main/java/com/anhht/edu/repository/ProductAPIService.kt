package com.anhht.edu.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Level
import com.anhht.edu.model.data.Product
import com.anhht.edu.network.LevelAPI
import com.anhht.edu.network.ProductAPI
import com.anhht.edu.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductAPIService {
    private var products: ArrayList<Product> = ArrayList()
    private val listproducts: MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
    fun getAllProducts(context: Context) : MutableLiveData<List<Product>> {
        val retrofit = ServiceBuilder.buildService(context, ProductAPI::class.java)
        retrofit.getAll()!!.enqueue(
            object : Callback<ResponseApi<List<Product>>> {
                override fun onResponse(
                    call: Call<ResponseApi<List<Product>>>,
                    response: Response<ResponseApi<List<Product>>>
                ) {
                    val data = response.body()
                    if(data?.data != null){
                        products = ArrayList(data.data)
                        Log.e("product", products.toString())
                        listproducts.value = products
                    }
                }
                override fun onFailure(call: Call<ResponseApi<List<Product>>>, t: Throwable) {
//                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("t", "l", t)
                }
            }
        )
        return listproducts
    }
}