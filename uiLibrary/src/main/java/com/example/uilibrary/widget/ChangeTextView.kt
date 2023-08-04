package com.example.uilibrary.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class ChangeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs), DefaultLifecycleObserver {
    private var mNowNum = 0
    private var mAnim: ValueAnimator? = null

    fun setTextNum(newNum: Int) {
        if (newNum == mNowNum) return
        mAnim?.cancel()
        mAnim = ObjectAnimator.ofInt(mNowNum, newNum)
        mAnim?.duration = 500
        mAnim?.start()
        mNowNum = newNum
        mAnim?.addUpdateListener {
            text = it.animatedValue.toString()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        mAnim?.cancel()
        mAnim?.removeAllListeners()
        super.onDestroy(owner)
    }
}