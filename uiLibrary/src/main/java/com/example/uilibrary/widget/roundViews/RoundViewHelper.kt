package com.example.uilibrary.widget.roundViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import com.example.uilibrary.R

class RoundViewHelper {
    private var mBorderWidth = 0f
    private var mBorderColor = Color.parseColor("#00000000")
    private var mOval = false
    private var mRvRadius = 0f
    private val mPath = Path()
    private val mPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundViews)
        mBorderWidth = typedArray.getDimension(R.styleable.RoundViews_riv_border_width, 0f)
        mBorderColor = typedArray.getColor(
            R.styleable.RoundViews_riv_border_color,
            Color.TRANSPARENT
        )
        mOval = typedArray.getBoolean(R.styleable.RoundViews_riv_oval, false)
        mRvRadius = typedArray.getDimension(R.styleable.RoundViews_riv_radius, 0f)
        mPaint.color = mBorderColor
        mPaint.strokeWidth = mBorderWidth
        typedArray.recycle()
    }

    fun onLayout(width: Float, height: Float) {
        if (mOval) {
            mPath.addOval(0f, 0f, width, height, Path.Direction.CW)
        } else if (mBorderWidth >= 0) {
            mPath.addRoundRect(
                0f, 0f, width,
                height, mRvRadius,
                mRvRadius, Path.Direction.CW
            )
        }
    }

    fun onDraw(canvas: Canvas?, width: Int, height: Int, callSuper: () -> Unit) {
        if (mOval) {
            canvas?.clipPath(mPath)
        } else if (mBorderWidth >= 0) {
            canvas?.clipPath(mPath)
        }
        callSuper()
        if (mOval) {
            canvas?.drawOval(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
        } else if (mBorderWidth >= 0) {
            canvas?.drawRoundRect(
                0f, 0f, width.toFloat(),
                height.toFloat(), mRvRadius,
                mRvRadius, mPaint
            )
        }
    }
}