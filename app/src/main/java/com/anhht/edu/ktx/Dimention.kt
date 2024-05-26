package com.anhht.edu.ktx

import android.content.res.Resources

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()