package com.anhht.edu.test

data class TestApi(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)