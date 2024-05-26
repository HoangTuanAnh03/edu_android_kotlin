package com.anhht.edu.model.data

import java.io.Serializable

data class Product(
    val pid:Int,
    val name:String,
    val price:Double,
    val image:String,
    val remain:Int
) : Serializable
