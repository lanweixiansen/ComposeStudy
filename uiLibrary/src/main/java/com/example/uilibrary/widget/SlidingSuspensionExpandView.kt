package com.example.uilibrary.widget

import android.content.Context
import android.util.AttributeSet
import com.airbnb.lottie.LottieAnimationView

class SlidingSuspensionExpandView @JvmOverloads constructor(
    context: Context, isLeft: Boolean, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LottieAnimationView(context, attrs, defStyleAttr) {

    init {
        this.setAnimation(if (isLeft) "lottie_left.json" else "lottie_right.json")
        playAnimation()
    }
}