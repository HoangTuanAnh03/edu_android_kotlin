package com.anhht.edu.model.data

import java.util.Date

data class OrderHistory (
    val date: Date,
     val address: String,
     val price: Double,
     val quantity: Int,
     val phone: String,
     val pname: String,
     val pimage: String,
)