package com.anhht.edu.model

data class ResponseApi<T>(
    val `data`: T,
    val message: String,
    val status: String
)