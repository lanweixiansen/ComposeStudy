package com.example.lib_news.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.uilibrary.widget.RoundImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RotationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RoundImageView(context, attrs), DefaultLifecycleObserver {
    private var mRadius = 1f
    private var mTime = 1f
    private var mJob: Job? = null


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.rotation = mRadius
    }

    fun startRotation() {
        mJob?.cancel()
        mJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(10)
                mRadius += mTime
                mTime += 0.01f
                invalidate()
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        mJob?.cancel()
    }

}