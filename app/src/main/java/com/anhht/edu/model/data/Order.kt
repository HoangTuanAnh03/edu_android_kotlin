package com.anhht.edu.model.data

import java.util.Date

data class Order(
    val uid:Int,
    val date: Date,
    val pid: Int,
    val quantity:Int,
    val price: Double,
    val phone: String,
    val address:String,
    )
