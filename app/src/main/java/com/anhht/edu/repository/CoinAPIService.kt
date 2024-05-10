package com.anhht.edu.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Level
import com.anhht.edu.model.data.TopicByLevel
import com.anhht.edu.network.CoinAPI
import com.anhht.edu.network.LevelAPI
import com.anhht.edu.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinAPIService {
    private var responseApiCoin : ResponseApi<Int> = ResponseApi<Int>(data = 0, message = "", status = "200")
    private val responseCoin: MutableLiveData<ResponseApi<Int>> = MutableLiveData<ResponseApi<Int>>()
    fun postAnswer(answer:String, wid:Int) : MutableLiveData<ResponseApi<Int>>{
        val retrofit = ServiceBuilder.buildService(CoinAPI::class.java)
        retrofit.postAnswer(answer, wid)!!.enqueue(
            object : Callback<ResponseApi<Int>> {
                override fun onResponse(
                    call: Call<ResponseApi<Int>>,
                    response: Response<ResponseApi<Int>>
                ) {
                    val data = response.body()
                    Log.e("data",data.toString())
                    responseApiCoin = data!!
                    responseCoin.value = responseApiCoin
                }
                override fun onFailure(call: Call<ResponseApi<Int>>, t: Throwable) {
                    Log.e("CoinAPI", t.message.toString())
                }
            }
        )
        return responseCoin
    }
}