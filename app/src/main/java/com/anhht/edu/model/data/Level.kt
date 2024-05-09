package com.anhht.edu.model.data

import java.io.Serializable

data class Level(
    val lid: Int,
    val levelName: String,
    val numTopics: Int,
    val numWords: Int,
    val process: Float,
) : Serializable