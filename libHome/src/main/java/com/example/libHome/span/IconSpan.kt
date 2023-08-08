package com.example.libHome.span

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.Rect
import android.graphics.RectF
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ReplacementSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.uilibrary.uiUtils.dp2px

class IconTextSpan(val context: Context, bgColorResId: Int, text: String) :
    ReplacementSpan() {
    private var mBgColorResId = 0 //Icon背景颜色
    private var mText: String? = null //Icon内文字
    private var mBgHeight = 0f //Icon背景高度
    private val mBgWidth: Float //Icon背景宽度
    private var mRadius = 0f //Icon圆角半径
    private var mRightMargin = dp2px(2f) //右边距
    private var mTextSize = 0f //文字大小
    private var mTextColorResId = 0 //文字颜色

    init {
        //初始化默认数值
        initDefaultValue(bgColorResId, text)
        //计算背景的宽度
        mBgWidth = calculateBgWidth(text)
    }

    /**
     * 初始化默认数值
     */
    private fun initDefaultValue(bgColorResId: Int, text: String) {
        mBgColorResId = bgColorResId
        mText = text
        mBgHeight = dp2px(17f)
        mRightMargin = dp2px(2f)
        mRadius = dp2px(2f)
        mTextSize = dp2px(12f)
        mTextColorResId = com.example.uilibrary.R.color.white
    }

    /**
     * 计算icon背景宽度
     *
     * @param text icon内文字
     */
    private fun calculateBgWidth(text: String): Float {
        return if (text.length > 1) {
            //多字，宽度=文字宽度+padding
            val textRect = Rect()
            val paint = Paint()
            paint.textSize = mTextSize
            paint.getTextBounds(text, 0, text.length, textRect)
            val padding = dp2px(4f)
            textRect.width() + padding * 2
        } else {
            //单字，宽高一致为正方形
            mBgHeight
        }
    }

    /**
     * 设置右边距
     *
     * @param rightMarginDpValue
     */
    fun setRightMarginDpValue(rightMarginDpValue: Int) {
        mRightMargin = dp2px(rightMarginDpValue.toFloat())
    }

    /**
     * 设置宽度，宽度=背景宽度+右边距
     */
    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: FontMetricsInt?
    ): Int {
        return (mBgWidth + mRightMargin).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        //画背景
        val bgPaint = Paint()
        bgPaint.color = ContextCompat.getColor(context, mBgColorResId)
        bgPaint.style = Paint.Style.FILL
        bgPaint.isAntiAlias = true
        val metrics = paint.fontMetrics
        val textHeight = metrics.descent - metrics.ascent
        //算出背景开始画的y坐标
        val bgStartY = y + (textHeight - mBgHeight) / 2 + metrics.ascent

        //画背景
        val bgRect = RectF(x, bgStartY, x + mBgWidth, bgStartY + mBgHeight)
        canvas.drawRoundRect(bgRect, mRadius, mRadius, bgPaint)

        //把字画在背景中间
        val textPaint = TextPaint()
        textPaint.color = ContextCompat.getColor(context, mTextColorResId)
        textPaint.textSize = mTextSize
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER //这个只针对x有效
        val fontMetrics = textPaint.fontMetrics
        val textRectHeight = fontMetrics.bottom - fontMetrics.top
        canvas.drawText(
            mText.toString(),
            x + mBgWidth / 2,
            bgStartY + (mBgHeight - textRectHeight) / 2 - fontMetrics.top,
            textPaint
        )
    }
}