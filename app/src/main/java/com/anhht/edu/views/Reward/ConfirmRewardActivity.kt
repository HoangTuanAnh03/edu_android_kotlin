package com.anhht.edu.views.Reward

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.airbnb.lottie.parser.IntegerParser
import com.anhht.edu.R
import com.anhht.edu.databinding.ActivityConfirmRewardBinding
import com.anhht.edu.model.data.Product
import com.anhht.edu.model.data.Topic
import com.anhht.edu.model.request.OrderRequest
import com.anhht.edu.repository.OrderAPIService
import com.anhht.edu.viewmodels.OrderViewModel
import com.squareup.picasso.Picasso

class ConfirmRewardActivity : AppCompatActivity(){
    lateinit var binding: ActivityConfirmRewardBinding
    lateinit var orderViewModel: OrderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras : Bundle? = intent.extras
        val product : Product = extras?.get("product") as Product
//        Log.e("product Re", product.toString())
        if(product != null){
            Picasso.get().load(product.image).into(binding.rewardImage)
            binding.rewardName.text = product.name
            binding.rewardPrice.text = String.format("\$%s dats", product.price.toString().replace("([.]*0+)(?!.*\\d)".toRegex(), ""))
        }

        binding.lessBtn.setOnClickListener{
            val value = binding.editTextNumber.text.toString().toInt()
            //Log.e("value", value.toString())
            if(value > 1){
                changeNumberProduct(value-1, product)
            }
        }
        binding.pushBtn.setOnClickListener{
            val value = binding.editTextNumber.text.toString().toInt()
            changeNumberProduct(value+1, product)
        }
        binding.rewardBack.setOnClickListener{
            finish()
        }
        binding.rewardConfirm.setOnClickListener{
            if((binding.rewardAddress.text != null) && (binding.rewardAddress.text.toString() != "")
                && (binding.rewardPhoneNumber.text != null) && (binding.rewardPhoneNumber.text.toString() != "")
                && (binding.rewardPhoneNumber.text.toString().matches("^0[0-9]{9}$".toRegex())) ){
                val orderRequest = OrderRequest(
                    binding.rewardPhoneNumber.text.toString(),
                    binding.rewardAddress.text.toString(),
                    product.pid,
                    binding.editTextNumber.text.toString().toInt()
                )
                orderViewModel = OrderViewModel(OrderAPIService())
                orderViewModel.addNewOrder(orderRequest).observe(this@ConfirmRewardActivity){d->
                    Toast.makeText(applicationContext, d.message, Toast.LENGTH_LONG).show()
                    if(d.data != null){
                        finish()
                    }
                }
            }else{
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun changeNumberProduct(value : Int, product:Product){
        binding.editTextNumber.setText(value.toString())
        binding.rewardPrice.text = String.format("\$%s dats", (value*product.price).toString().replace("([.]*0+)(?!.*\\d)".toRegex(), ""))
    }
}