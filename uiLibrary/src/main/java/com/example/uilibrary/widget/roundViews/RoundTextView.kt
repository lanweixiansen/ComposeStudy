package com.example.uilibrary.widget.roundViews

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class RoundTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {
    private val helper: RoundViewHelper = RoundViewHelper()

    init {
        helper.init(context, attrs, typedArray)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        helper.onLayout(width.toFloat(), height.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        helper.onDraw(canvas, width, height) {
            super.onDraw(canvas)
        }
    }
}