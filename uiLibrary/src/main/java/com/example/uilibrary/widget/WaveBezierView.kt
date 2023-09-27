package com.example.uilibrary.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.math.roundToInt

/**
 * 心型进度贝塞尔曲线
 */
class WaveBezierView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr), LifecycleEventObserver {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG) //波浪画笔
    private val path = Path()
    private val mPath = Path() //波浪Path类
    private val mWaveLength = 100 //一个波浪长度
    private var mWaveCount = 0 //波纹个数
    private var mOffset = 0 //平移偏移量
    private var mScreenHeight = 0 //屏幕高度
    private var mScreenWidth = 0 //屏幕宽度
    private var mCenterY = 0 //波纹的中间轴
    var animator: ValueAnimator? = null
    private var mPro = 0;

    init {
        mPaint.color = Color.parseColor("#FF467B")
        mPaint.style = Paint.Style.FILL_AND_STROKE
        startAnim()
    }

    //获取宽和高
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mScreenHeight = h
        mScreenWidth = w
        //加1.5：至少保证波纹有2个，至少2个才能实现平移效果
        mWaveCount = (mScreenWidth / mWaveLength + 1.5).roundToInt()
        setProgress(mPro)
    }

    //绘制水波纹
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        path.reset()
        path.moveTo(width.toFloat() / 2, height.toFloat())
        path.quadTo(
            width.toFloat() * 1.3f,
            height.toFloat() / 3,
            width.toFloat() - width.toFloat() / 8,
            height.toFloat() / 24
        )
        path.lineTo(width.toFloat() / 2, height.toFloat() / 16)
        path.moveTo(width.toFloat() / 2, height.toFloat())
        path.quadTo(
            width - width.toFloat() * 1.3f,
            height.toFloat() / 3,
            width.toFloat() / 8,
            height.toFloat() / 24
        )
        path.lineTo(width.toFloat() / 2, height.toFloat() / 16)
        canvas.clipPath(path)
        mPath.reset()
        mPath.moveTo((-mWaveLength + mOffset).toFloat(), mCenterY.toFloat()) //移到屏幕外最左边
        for (i in 0 until mWaveCount) {
            //正弦曲线
            mPath.quadTo(
                (-mWaveLength * 3 / 4 + i * mWaveLength + mOffset).toFloat(),
                (mCenterY + 10).toFloat(),
                (-mWaveLength / 2 + i * mWaveLength + mOffset).toFloat(),
                mCenterY.toFloat()
            )
            mPath.quadTo(
                (-mWaveLength / 4 + i * mWaveLength + mOffset).toFloat(),
                (mCenterY - 10).toFloat(),
                (i * mWaveLength + mOffset).toFloat(),
                mCenterY.toFloat()
            )
        }
        //填充矩形
        mPath.lineTo(mScreenWidth.toFloat(), mScreenHeight.toFloat())
        mPath.lineTo(0f, mScreenHeight.toFloat())
        mPath.close()
        canvas.drawPath(mPath, mPaint)
    }

    //实现平移效果
    private fun startAnim() {
        animator = ValueAnimator.ofInt(0, mWaveLength)
        animator?.duration = 1000
        animator?.repeatCount = ValueAnimator.INFINITE
        animator?.interpolator = LinearInterpolator()
        animator?.addUpdateListener { animation ->
            mOffset = animation.animatedValue as Int
            postInvalidate()
        }
        animator?.start()
    }

    fun setProgress(progress: Int) {
        mPro = progress
        val pro = when {
            progress >= 100 -> 100
            progress <= 0 -> 0
            else -> progress
        }
        if ((100 - pro) == 0) {
            mCenterY = 0
            invalidate()
            return
        }
        mCenterY = (height * (100 - pro) * 0.01).toInt()
        invalidate()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            animator?.cancel()
            animator?.removeAllUpdateListeners()
            animator = null
        }
    }
}