package com.example.uilibrary.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MyFoldTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val s = text

    }
}