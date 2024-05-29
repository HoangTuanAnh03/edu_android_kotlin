package com.anhht.edu.repository

import android.content.Context
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Question
import com.anhht.edu.network.ServiceBuilder
import com.anhht.edu.network.WordAPI
import com.anhht.edu.utils.NoInternetException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WordAPIService {
    private var questions: ArrayList<Question> = ArrayList()
    private val listQuestion: MutableLiveData<List<Question>> = MutableLiveData<List<Question>>()
    private val retrofit = ServiceBuilder.buildService(WordAPI::class.java)
    private val responseTest: MutableLiveData<ResponseApi<List<Question>>> = MutableLiveData<ResponseApi<List<Question>>>()
    fun getQuestionByTid(context: Context, tid:Int) : MutableLiveData<List<Question>> {
        val retro = ServiceBuilder.buildService(context, WordAPI::class.java)
        retro.getWordsByTid(tid)!!.enqueue(
            object : Callback<ResponseApi<List<Question>>> {
                override fun onResponse(
                    call: Call<ResponseApi<List<Question>>>,
                    response: Response<ResponseApi<List<Question>>>
                ) {
                    var data = response.body()
                    Log.e("data",data.toString())
                    if(data?.data != null){
                        questions = ArrayList(data.data)
                        listQuestion.value = questions
                    }
                }
                override fun onFailure(call: Call<ResponseApi<List<Question>>>, t: Throwable) {
//                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("Word", t.toString())
                }
            }
        )
        return listQuestion
    }
    fun getQuestionByTidTest(context: Context, tid:Int) : MutableLiveData<List<Question>> {
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
//                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("Word", t.toString())
                }
            }
        )
        return listQuestion
    }

    fun getTest(context: Context) : MutableLiveData<ResponseApi<List<Question>>>{
        val retrofit = ServiceBuilder.buildService(WordAPI::class.java)
        retrofit.getTest()!!.enqueue(
            object : Callback<ResponseApi<List<Question>>> {
                override fun onResponse(
                    call: Call<ResponseApi<List<Question>>>,
                    response: Response<ResponseApi<List<Question>>>
                ) {
                    var data = response.body()
                    responseTest.value = data
                }
                override fun onFailure(call: Call<ResponseApi<List<Question>>>, t: Throwable) {
//                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    Log.e("Word", t.toString())
                }
            }
        )
//        val retrofit = ServiceBuilder.buildService(WordAPI::class.java)
//        try {
//            val response = retrofit.getTest()?.execute()
//            if(response!!.isSuccessful){
//                val data = response.body()
//                responseTest.value = data
//            }else{
//                Log.e("CoinActivitya", "Error fetching coins:")
//            }
//        }catch (e: IOException){
//            Log.e("CoinActivity", "Error fetching coins: ${e.message}")
//        }catch (e : NoInternetException){
//            Log.e("NoInternetException", "Error fetching coins: ${e.message}")
//        }catch (e: NetworkOnMainThreadException){
//            Log.e("NoInternetException", "Error fetching coins: ${e.message}")
//        }
        return responseTest
    }
}