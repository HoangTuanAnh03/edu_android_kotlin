package com.anhht.edu.views

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityTestBinding


class TestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTestBinding
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTestBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val endcall0 = findViewById<View>(R.id.activity_main_btn)

        val text = findViewById<TextView>(R.id.btn_loading_layout_tv)

        text.text = "hihi"

//        val btn = findViewById<View>(com.anhht.edu.R.id.activity_main_btn) as LinearLayout


        endcall0.setOnClickListener{
            val progressbar = BtnLoadingProgressbar(it) // `it` is view of button
            progressbar.setLoading()
            handler.postDelayed({
//                progressbar.setState(true){ // executed after animation end
////                    handler.postDelayed({
////                        startError(progressbar)
////                    },1500)
//                }
                                progressbar.reset()
            },2000)
        }

//        binding.activityMainBtn.setOnClickListener {
//            val progressbar = BtnLoadingProgressbar(it) // `it` is view of button
//            progressbar.setLoading()
//            handler.postDelayed({
//                progressbar.setState(true){ // executed after animation end
//                    handler.postDelayed({
//                        startError(progressbar)
//                    },1500)
//                }
//            },2000)
//        }
    }

    private fun startError(progressbar: BtnLoadingProgressbar) {
        progressbar.reset()
        handler.postDelayed({
            progressbar.setLoading()
            handler.postDelayed({
                progressbar.setState(false){ // executed after animation end
                    handler.postDelayed({
                        progressbar.reset()
                    },1500)
                }
            },2000)
        },600)
    }
}