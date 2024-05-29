package com.anhht.edu.network

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.anhht.edu.R
import com.anhht.edu.databinding.NetworkOfflineOnlineLayoutBinding
import com.anhht.edu.utils.DialogUtils
import com.anhht.edu.utils.NoInternetException
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(val context: Context) : Interceptor {
    private lateinit var bottomSheetDialogOffline: BottomSheetDialog
    private lateinit var networkErrorWithDisableAllViews: LinearLayout
    private lateinit var networkBackOnline: LinearLayout
    private val handler = Handler(Looper.getMainLooper())
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isInternetAvailable()){
            handler.post {
//                bottomSheetDialogOffline = BottomSheetDialog(context, R.style.BottomSheetDialog)
//                val binding = NetworkOfflineOnlineLayoutBinding.inflate(LayoutInflater.from(context))
//                bottomSheetDialogOffline.setContentView(binding.root)
//                bottomSheetDialogOffline.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                networkErrorWithDisableAllViews = binding.disableAllViews
//                networkBackOnline = binding.layoutNetworkBackOnline
//                DialogUtils.showNetworkError(networkErrorWithDisableAllViews, networkBackOnline)
//                bottomSheetDialogOffline.setCancelable(false)
//                bottomSheetDialogOffline.show()
//                binding.disableAllViews.findViewById<Button>(R.id.retryButton).setOnClickListener{
//                    val intent = Intent(context, context::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    context.startActivity(intent)
//
//                    Log.e("data", "data")
//                    bottomSheetDialogOffline.dismiss()
//                }
                Toast.makeText(context, "Vui lòng kết nối mạng!!", Toast.LENGTH_SHORT).show()
            }
            throw NoInternetException("Vui lòng kết nối mạng!!")

        }
        return chain.proceed(chain.request())
    }
    fun isInternetAvailable() : Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}