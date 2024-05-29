package com.anhht.edu.repository

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anhht.edu.R
import com.anhht.edu.databinding.NetworkOfflineOnlineLayoutBinding
import com.anhht.edu.model.ResponseApi
import com.anhht.edu.model.data.Level
import com.anhht.edu.network.LevelAPI
import com.anhht.edu.network.ServiceBuilder
import com.anhht.edu.utils.DialogUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LevelAPIService {

    private var levels: ArrayList<Level> = ArrayList<Level>()
    private val listLevels: MutableLiveData<List<Level>> = MutableLiveData<List<Level>>()
    fun getAllLevel(context: Context) : MutableLiveData<List<Level>> {
        val retrofit = ServiceBuilder.buildService(context, LevelAPI::class.java)
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
//                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
//                    bottomSheetDialogOffline = BottomSheetDialog(context, R.style.BottomSheetDialog)
//                    val binding = NetworkOfflineOnlineLayoutBinding.inflate(LayoutInflater.from(context))
//                    bottomSheetDialogOffline.setContentView(binding.root)
//                    bottomSheetDialogOffline.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//                    networkErrorWithDisableAllViews = binding.disableAllViews
//                    networkBackOnline = binding.layoutNetworkBackOnline
//
//                    DialogUtils.showNetworkError(networkErrorWithDisableAllViews, networkBackOnline)
//
//                    bottomSheetDialogOffline.setCancelable(false)
//                    bottomSheetDialogOffline.show()
                    Log.e("t", "l", t)
                }
            }
        )
        return listLevels
    }
}