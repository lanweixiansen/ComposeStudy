package com.example.lib_news.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.uilibrary.widget.roundViews.RoundImageView
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
    private var mIsRunning = false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.rotation = mRadius
    }

    private fun startRotation() {
        mJob?.cancel()
        mIsRunning = true
        mJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(10)
                mRadius += mTime
                mTime += 0.01f
                invalidate()
            }
        }
    }

    private fun endRotation() {
        mJob?.cancel()
        mIsRunning = false
    }

    fun beginRotation() {
        if (mIsRunning) {
            endRotation()
        } else {
            startRotation()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        mJob?.cancel()
    }
}