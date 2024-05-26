package com.anhht.edu.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Question
import com.anhht.edu.network.ServiceBuilder
import com.anhht.edu.network.WordAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordAPIService {
    private var questions: ArrayList<Question> = ArrayList()
    private val listQuestion: MutableLiveData<List<Question>> = MutableLiveData<List<Question>>()
    private val retrofit = ServiceBuilder.buildService(WordAPI::class.java)
    private val responseTest: MutableLiveData<ResponseApi<List<Question>>> = MutableLiveData<ResponseApi<List<Question>>>()
    fun getQuestionByTid(tid:Int) : MutableLiveData<List<Question>> {
        //val retrofit = ServiceBuilder.buildService(WordAPI::class.java)
        retrofit.getWordsByTid(tid)!!.enqueue(
            object : Callback<ResponseApi<List<Question>>> {
                override fun onResponse(
                    call: Call<ResponseApi<List<Question>>>,
                    response: Response<ResponseApi<List<Question>>>
                ) {
                    var data = response.body()
                    Log.e("data",data.toString())
                    if(data?.data != null){
                        questions = ArrayList(data?.data)
                        listQuestion.value = questions
                    }
                }
                override fun onFailure(call: Call<ResponseApi<List<Question>>>, t: Throwable) {
                    Log.e("Word", t.toString())
                }
            }
        )
        return listQuestion
    }
    fun getQuestionByTidTest(tid:Int) : MutableLiveData<List<Question>> {
        //val retrofit = ServiceBuilder.buildService(WordAPI::class.java)
        retrofit.getWordsByTidTest(tid)!!.enqueue(
            object : Callback<ResponseApi<List<Question>>> {
                override fun onResponse(
                    call: Call<ResponseApi<List<Question>>>,
                    response: Response<ResponseApi<List<Question>>>
                ) {
                    var data = response.body()
                    if(data?.data != null){
                        questions = ArrayList(data.data)
                        listQuestion.value = questions
                    }
                }
                override fun onFailure(call: Call<ResponseApi<List<Question>>>, t: Throwable) {
                    Log.e("Word", t.toString())
                }
            }
        )
        return listQuestion
    }

    fun getTest() : MutableLiveData<ResponseApi<List<Question>>>{
        //val retrofit = ServiceBuilder.buildService(WordAPI::class.java)
        retrofit.getTest()!!.enqueue(
            object : Callback<ResponseApi<List<Question>>> {
                override fun onResponse(
                    call: Call<ResponseApi<List<Question>>>,
                    response: Response<ResponseApi<List<Question>>>
                ) {
                    var data = response.body()
                    //if(data?.data != null){
                        //questions = ArrayList(data.data)
                    responseTest.value = data
                    //}
                }
                override fun onFailure(call: Call<ResponseApi<List<Question>>>, t: Throwable) {
                    Log.e("Word", t.toString())
                }
            }
        )
        return responseTest
    }
}