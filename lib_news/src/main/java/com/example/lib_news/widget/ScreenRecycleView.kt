package com.example.lib_news.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ScreenRecycleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {
    private var mCanAuto = true
    private var mMsgCount = 0
    private var mOnUpdate: ((Int) -> Unit)? = null

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (e?.action == MotionEvent.ACTION_DOWN) {
            mCanAuto = false
        }
        if (e?.action == MotionEvent.ACTION_UP) {
            mCanAuto = true
        }
        return super.onTouchEvent(e)
    }


    override fun scrollToPosition(position: Int) {
        if (mCanAuto && this.scrollState == SCROLL_STATE_IDLE) {
            super.scrollToPosition(position)
            mMsgCount = 0
        } else {
            mMsgCount++
        }
        mOnUpdate?.invoke(mMsgCount)
    }

    fun setOnUnReadUpdateListener(onUpdate: (num: Int) -> Unit) {
        mOnUpdate = onUpdate
    }
}