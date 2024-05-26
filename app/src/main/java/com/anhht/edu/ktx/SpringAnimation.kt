package com.anhht.edu.ktx

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

fun View.springAnimate(
    startScaleX: Float = 1.2f,
    startScaleY: Float = 1.2f,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_HIGH_BOUNCY,
    stiffness: Float = SpringForce.STIFFNESS_MEDIUM
) {
    scaleX = startScaleX
    scaleY = startScaleY
    SpringAnimation(this, DynamicAnimation.SCALE_X, 1f)
        .apply {
            spring.dampingRatio = dampingRatio
            spring.stiffness = stiffness
        }
        .start()
    SpringAnimation(this, DynamicAnimation.SCALE_Y, 1f)
        .apply {
            spring.dampingRatio = dampingRatio
            spring.stiffness = stiffness
        }
        .start()
}