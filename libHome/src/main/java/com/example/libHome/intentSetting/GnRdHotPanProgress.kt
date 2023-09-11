package com.example.libHome.intentSetting

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ProgressBar
import com.example.uilibrary.uiUtils.dp2px

/**
 * 热点选股-热点-盘面分析ProgressBar
 */
class GnRdHotPanProgress @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ProgressBar(context, attrs) {
    private var mWidth = 0f

    /**
     * 左边画笔
     */
    private val mLeftPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#F06C6E")
    }

    /**
     * 中间画笔
     */
    private val mCenterPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#999999")
    }

    /**
     * 右边画笔
     */
    private val mRightPaint = Paint().apply {
        isAntiAlias = true
        color = Color.parseColor("#4CB852")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        // 计算中间位置
        val center = mWidth * (progress / 100f)
        // 绘制左边图形
        canvas?.drawRect(0f, 0f, center - dp2px(4f), dp2px(8f), mLeftPaint)
        // 绘制中间图形
        canvas?.drawRect(center - dp2px(3f), 0f, center + dp2px(3f), dp2px(8f), mCenterPaint)
        // 绘制右边图形
        canvas?.drawRect(center + dp2px(4f), 0f, mWidth, dp2px(8f), mRightPaint)
    }

    override fun setProgress(progress: Int) {
        super.setProgress(progress)
        invalidate()
    }
}