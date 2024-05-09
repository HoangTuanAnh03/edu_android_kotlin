package com.anhht.edu.model.data

import java.io.Serializable

data class Topic(
    val tid: Int,
    val topic: String,
    val lid: Int,
    val process: Float,
) : Serializable
