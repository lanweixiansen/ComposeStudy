package com.example.uilibrary.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import com.airbnb.lottie.LottieAnimationView
import com.example.uilibrary.R

class CustomAdapterHeader @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {
    private val lottieView: LottieAnimationView

    init {
        lottieView = inflate(
            context,
            R.layout.base_adapter_header_layput,
            this
        ).findViewById(R.id.lottie_view)
    }

    fun startPlay() {
        lottieView.playAnimation()
    }
}