package com.anhht.edu.model.request

import com.google.gson.annotations.SerializedName

data class OrderRequest (
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: String,
    @SerializedName("pid") val pid : Int,
    @SerializedName("quantity") val quantity: Int
)