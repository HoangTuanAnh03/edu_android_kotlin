package com.anhht.edu.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Product
import com.anhht.edu.model.data.TestHistory
import com.anhht.edu.network.HistoryAPI
import com.anhht.edu.network.ProductAPI
import com.anhht.edu.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestHistoryAPIService {
    private val history: MutableLiveData<TestHistory> = MutableLiveData<TestHistory>()
    private val historyDetails: MutableLiveData<List<TestHistory>> = MutableLiveData<List<TestHistory>>()
    fun addTestHistory(context: Context, numQues : Int, numCorrectQues : Int) : MutableLiveData<TestHistory> {
        val retrofit = ServiceBuilder.buildService(context, HistoryAPI::class.java)
        retrofit.addTestHistory(numQues, numCorrectQues)!!.enqueue(
            object : Callback<ResponseApi<TestHistory>> {
                override fun onResponse(
                    call: Call<ResponseApi<TestHistory>>,
                    response: Response<ResponseApi<TestHistory>>
                ) {
                    val data = response.body()
                    history.value = data?.data
                }
                override fun onFailure(call: Call<ResponseApi<TestHistory>>, t: Throwable) {
//                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("t", "l", t)
                }
            }
        )
        return history
    }

    fun getAllTestHistory(context: Context) : MutableLiveData<List<TestHistory>> {
        val retrofit = ServiceBuilder.buildService(context, HistoryAPI::class.java)
        retrofit.getAllTestHistory()!!.enqueue(
            object : Callback<ResponseApi<List<TestHistory>>> {
                override fun onResponse(
                    call: Call<ResponseApi<List<TestHistory>>>,
                    response: Response<ResponseApi<List<TestHistory>>>
                ) {
                    val data = response.body()
                    historyDetails.value = data?.data
                }
                override fun onFailure(call: Call<ResponseApi<List<TestHistory>>>, t: Throwable) {
//                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("t", "l", t)
                }
            }
        )
        return historyDetails
    }
}