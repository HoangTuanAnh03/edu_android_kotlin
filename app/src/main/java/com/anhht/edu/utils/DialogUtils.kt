package com.anhht.edu.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager

class DialogUtils {

    companion object {

        private var animationDuration: Long = 5000
        private var animAlphaHide = 0.0f
        private var animAlphaUnhide = 1.0f

        fun enableDisableView(v: View, enabled: Boolean) {
            if (v is ViewGroup) {
                for (i in 0 until v.childCount) {
                    enableDisableView(v.getChildAt(i), enabled)
                }
            }
            v.isEnabled = enabled
        }
        fun showNetworkError(offlineView: ViewGroup, onlineView: ViewGroup) {

            onlineView.apply {
                animate()
                    .alpha(animAlphaUnhide)
            }

            val transition: Transition = Fade().apply {
                duration = animationDuration
                addTarget(offlineView)
            }
            TransitionManager.beginDelayedTransition(offlineView, transition)
            offlineView.visibility = View.VISIBLE
        }
        fun showNetworkBackOnline(offlineView: ViewGroup, onlineView: View) {
            onlineView.visibility = View.VISIBLE
            onlineView.apply {
                animate()
                    .alpha(animAlphaHide)
                    .setDuration(animationDuration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            onlineView.visibility = View.GONE
                        }
                    })
            }
            offlineView.visibility = View.GONE
        }
    }
}