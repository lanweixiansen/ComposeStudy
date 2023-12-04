package com.example.uilibrary.widget.roundViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.uilibrary.R

open class RoundImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {
    private var mBorderWidth = 0f
    private var mBorderColor = Color.parseColor("#00000000")
    private var mOval = false
    private var mRvRadius = 0f
    private val mPath = Path()
    private val mPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    init {
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

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (mOval) {
            mPath.addOval(0f, 0f, width.toFloat(), height.toFloat(), Path.Direction.CW)
        } else if (mBorderWidth >= 0) {
            mPath.addRoundRect(
                0f, 0f, width.toFloat(),
                height.toFloat(), mRvRadius,
                mRvRadius, Path.Direction.CW
            )
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (mOval) {
            canvas?.clipPath(mPath)
        } else if (mBorderWidth >= 0) {
            canvas?.clipPath(mPath)
        }
        super.onDraw(canvas)
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