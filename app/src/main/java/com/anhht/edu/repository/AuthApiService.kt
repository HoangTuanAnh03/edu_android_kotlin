package com.anhht.edu.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.model.data.Data
import com.anhht.edu.model.request.RegisterRequest
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Register
import com.anhht.edu.model.request.ChangePasswordRequest
import com.anhht.edu.model.request.LoginRequest
import com.anhht.edu.network.AuthApi
import com.anhht.edu.network.CoinAPI
import com.anhht.edu.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthApiService {
    fun signIn(userData: LoginRequest, onResult: (ResponseApi<Data>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(AuthApi::class.java)
        retrofit.signIn(userData).enqueue(

            object : Callback<ResponseApi<Data>> {
                override fun onFailure(call: Call<ResponseApi<Data>>, t: Throwable) {
                    Log.e("Login", "Error : " + t.message)
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<ResponseApi<Data>>,
                    response: Response<ResponseApi<Data>>
                ) {
                    val responseApi = response.body()
                    onResult(responseApi)
                }
            }
        )
    }

    fun getAllLevel(context: Context, onResult: (ResponseApi<String>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(context, AuthApi::class.java)
        retrofit.getAll()!!.enqueue(
            object : Callback<ResponseApi<String>> {
                override fun onFailure(call: Call<ResponseApi<String>>, t: Throwable) {
                    Log.e("Login", "Error : " + t.message)
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<ResponseApi<String>>,
                    response: Response<ResponseApi<String>>
                ) {
                    val responseApi = response.body()
                    onResult(responseApi)
                }
            }
        )
    }

    fun exitEmail(email: String, onResult: (ResponseApi<String>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(AuthApi::class.java)
        retrofit.exitEmail(email).enqueue(
            object : Callback<ResponseApi<String>> {
                override fun onFailure(call: Call<ResponseApi<String>>, t: Throwable) {
                    Log.e("exitEmail", "Error : " + t.message)
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<ResponseApi<String>>,
                    response: Response<ResponseApi<String>>
                ) {
                    val responseApi = response.body()
                    onResult(responseApi)
                }
            }
        )
    }

    fun register(registerRequest: RegisterRequest, onResult: (ResponseApi<Register>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(AuthApi::class.java)
        retrofit.register(registerRequest).enqueue(
            object : Callback<ResponseApi<Register>> {
                override fun onFailure(call: Call<ResponseApi<Register>>, t: Throwable) {
                    Log.e("Register", "Error : " + t.message)
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<ResponseApi<Register>>,
                    response: Response<ResponseApi<Register>>
                ) {
                    val responseApi = response.body()
                    onResult(responseApi)
                }
            }
        )
    }

    fun forgetPassword(email: String, onResult: (ResponseApi<String>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(AuthApi::class.java)
        retrofit.forgetPassword(email).enqueue(
            object : Callback<ResponseApi<String>> {
                override fun onFailure(call: Call<ResponseApi<String>>, t: Throwable) {
                    Log.e("Forget Password", "Error : " + t.message)
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<ResponseApi<String>>,
                    response: Response<ResponseApi<String>>
                ) {
                    val responseApi = response.body()
                    onResult(responseApi)
                }
            }
        )
    }

    fun checkOtp(email: String, otp: String, onResult: (ResponseApi<String>?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(AuthApi::class.java)
        retrofit.checkOtp(email, otp).enqueue(
            object : Callback<ResponseApi<String>> {
                override fun onFailure(call: Call<ResponseApi<String>>, t: Throwable) {
                    Log.e("CheckOtp", "Error : " + t.message)
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<ResponseApi<String>>,
                    response: Response<ResponseApi<String>>
                ) {
                    Log.e("API", "hihi : " + response.body())

                    val responseApi = response.body()
                    onResult(responseApi)
                }
            }
        )
    }

    fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
        onResult: (ResponseApi<String>?) -> Unit
    ) {
        val retrofit = ServiceBuilder.buildService(AuthApi::class.java)
        retrofit.changePassword(changePasswordRequest).enqueue(
            object : Callback<ResponseApi<String>> {
                override fun onFailure(call: Call<ResponseApi<String>>, t: Throwable) {
                    Log.e("Change Password", "Error : " + t.message)
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<ResponseApi<String>>,
                    response: Response<ResponseApi<String>>
                ) {
                    val responseApi = response.body()
                    onResult(responseApi)
                }
            }
        )
    }
}