package com.example.libHome.sliding

import android.app.Application
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lib_base.ext.toast
import com.example.lib_home.R
import com.example.spk.sliding.SlidingUtils

class SlidingTestView @JvmOverloads constructor(
    context: Context, content: String, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private var mOnCloseClick: (() -> Unit)? = null

    init {
        inflate(context, R.layout.home_view_sliding_test, this)
        findViewById<TextView>(R.id.tv_test).apply {
            text = "$content 悬浮窗"
            setOnClickListener {
                "点击了悬浮窗${(parent as ViewGroup).childCount}".toast()
            }
        }
        findViewById<ImageView>(R.id.btn_close).setOnClickListener {
            SlidingUtils.removeView(this)
            mOnCloseClick?.invoke()
        }
    }

    fun setOnCloseClickListener(onCloseClick: () -> Unit) {
        this.mOnCloseClick = onCloseClick
    }
}