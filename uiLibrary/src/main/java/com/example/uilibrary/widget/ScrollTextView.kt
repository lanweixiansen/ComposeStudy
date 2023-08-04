package com.example.uilibrary.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.uilibrary.R
import com.example.uilibrary.uiUtils.dp2px

/**
 * 滚动变化TextView
 */
class ScrollTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {
    private val typeArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollTextView)
    private val mTextPaint = Paint().apply {
        isAntiAlias = true
    }
    private var mContent = "0"
    private var mTextWidthArray: FloatArray = FloatArray(mContent.length)
    private var mTextWidth = 0

    init {
        mTextPaint.textSize = typeArray.getDimension(R.styleable.ScrollTextView_sl_textSize, 16f)
        mTextPaint.color = typeArray.getColor(
            R.styleable.ScrollTextView_sl_textColor,
            ContextCompat.getColor(context, R.color.white)
        )
        mTextPaint.isFakeBoldText =
            typeArray.getInt(R.styleable.ScrollTextView_sl_textStyle, 0) == 1
        mContent = typeArray.getString(R.styleable.ScrollTextView_sl_text) ?: "0"
        getTextWidth()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mTextWidth, dp2px(50f).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawText(mContent, 0f, height / 1.5f, mTextPaint)
    }

    private fun getTextWidth() {
        mTextWidthArray = FloatArray(mContent.length)
        mTextPaint.getTextWidths(mContent, mTextWidthArray)
        mTextWidth = mTextWidthArray.sumOf { it.toInt() }
    }

    fun setText(content: String?) {
        mContent = content ?: "0"
        getTextWidth()
        requestLayout()
    }
}

