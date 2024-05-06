package com.anhht.edu.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Level
import com.anhht.edu.network.LevelAPI
import com.anhht.edu.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LevelAPIService {
    private var levels: ArrayList<Level> = ArrayList<Level>()
    private val listLevels: MutableLiveData<List<Level>> = MutableLiveData<List<Level>>()
    fun getAllLevel() : MutableLiveData<List<Level>> {

        val retrofit = ServiceBuilder.buildService(LevelAPI::class.java)
        retrofit.getAll()!!.enqueue(
            object : Callback<ResponseApi<List<Level>>> {
                override fun onResponse(
                    call: Call<ResponseApi<List<Level>>>,
                    response: Response<ResponseApi<List<Level>>>
                ) {
                    val data = response.body()
                    Log.e("data",data.toString())
                    if(data?.data != null){
                        levels = ArrayList(data.data)
                        Log.e("level", levels.toString())
                        listLevels.value = levels
                    }
                }
                override fun onFailure(call: Call<ResponseApi<List<Level>>>, t: Throwable) {
                    Log.e("t", "l", t)
                }
            }
        )
        return listLevels
    }
}