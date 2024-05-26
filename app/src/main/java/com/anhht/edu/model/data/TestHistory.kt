package com.anhht.edu.model.data

import java.util.Date

data class TestHistory (
    val thid : Int,
    val uid : Int,
    val numques : Int,
    val numcorrectques : Int,
    val tdate : Date
)