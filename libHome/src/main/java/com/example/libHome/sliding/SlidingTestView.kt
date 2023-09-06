package com.example.libHome.sliding

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.lib_base.ext.toast
import com.example.uilibrary.uiUtils.dp2px

class SlidingTestView @JvmOverloads constructor(
    context: Context, content: String, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    init {
        text = "$content 悬浮窗"
        background = (ContextCompat.getDrawable(context, com.example.uilibrary.R.drawable.bg_view))
        setPadding(dp2px(10), dp2px(40), dp2px(10), dp2px(40))
        setTextColor(Color.WHITE)
        this.setOnClickListener {
            "点击了悬浮窗".toast()
        }
    }
}