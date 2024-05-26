package com.anhht.edu.views.learn.Animation

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import com.anhht.edu.R

class Coin(context: Context) : AppCompatImageView(context) {

    init {
        setImageResource(R.drawable.coin)
    }

    companion object {
        fun addTo(
            frameLayout: FrameLayout,
            layoutParams: FrameLayout.LayoutParams =
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                    .apply {
                        gravity = Gravity.CENTER
                    }
        ): Coin {
            val coin = Coin(frameLayout.context)
            coin.layoutParams = layoutParams
            frameLayout.addView(coin)
            return coin
        }
    }

}