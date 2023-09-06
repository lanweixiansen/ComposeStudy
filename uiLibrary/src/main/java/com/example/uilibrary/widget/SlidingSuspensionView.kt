package com.example.uilibrary.widget

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.BarUtils
import com.example.uilibrary.uiUtils.dp2px
import com.example.uilibrary.uiUtils.getScreenHeight
import com.example.uilibrary.uiUtils.isEqualType
import com.example.uilibrary.uiUtils.saveAs
import kotlin.math.abs

class SlidingSuspensionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), DefaultLifecycleObserver {
    /**
     * 判断用户是点击事件还是滑动事件
     */
    private var mOldX = 0f
    private var mOldY = 0f

    /**
     * 记录当前点击位置
     */
    private var mOldRawX = 0f
    private var mOldRawY = 0f

    /**
     * 记录当前View移动位置
     */
    private var mOldTranslationX = 0f
    private var mOldTranslationY = 0f

    /**
     * 父布局的宽高，及悬浮窗View的最大展示范围
     */
    private var mMaxWidth = 0
    private var mMaxHeight = 0

    /**
     * 滑动阻力系数，越界之后阻力系数越来越大
     */
    private var mCoefficient = 0f

    /**
     * 边距
     */
    private var mMargin = dp2px(8f)

    private val lifecycle by lazy {
        if (context.isEqualType<LifecycleOwner>())
            context.saveAs<LifecycleOwner>().lifecycle else null
    }

    init {
        lifecycle?.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var interceptor = false
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mOldX = ev.x
                mOldY = ev.y
                mMaxWidth = (parent as? ViewGroup)?.width ?: 0
                mMaxHeight = (parent as? ViewGroup)?.height ?: 0
                mOldRawX = ev.rawX
                mOldRawY = ev.rawY
                mOldTranslationX = this.translationX
                mOldTranslationY = this.translationY
            }

            MotionEvent.ACTION_MOVE -> {
                // 误差10以内视为点击事件处理
                interceptor = abs(mOldX - ev.x) > 10 || abs(mOldY - ev.y) > 10
            }
        }
        return interceptor
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {}

            MotionEvent.ACTION_MOVE -> {
                this.translationX = (event.rawX - mOldRawX + mOldTranslationX)
                this.translationY = (event.rawY - mOldRawY + mOldTranslationY)
            }

            MotionEvent.ACTION_UP -> {
                showAnim(this.x, this.y, event.rawY)
            }
        }
        return true
    }

    /**
     * View回弹事件，高度增加状态栏及导航栏判断
     */
    private fun showAnim(rawX: Float, rawY: Float, rawY1: Float) {
        if ((rawX + this.width / 2) <= mMaxWidth / 2) {
            startAnim(-(this.left - mMargin), true)
        } else {
            startAnim(mMaxWidth - this.right - mMargin, true)
        }
        val barHeight =
            if ((rawY1 - this.height / 2) <= BarUtils.getStatusBarHeight() * 2)
                BarUtils.getStatusBarHeight()
            else if (BarUtils.isNavBarVisible(context as Activity)
                && (rawY1 + this.height / 2 + BarUtils.getNavBarHeight()) >= getScreenHeight()
            )
                BarUtils.getNavBarHeight()
            else 0
        if (rawY >= mMaxHeight - mMargin - this.height - barHeight) {
            startAnim(mMaxHeight - this.bottom - mMargin, false)
        } else if (rawY <= mMargin + barHeight) {
            startAnim(-(this.top - mMargin - barHeight), false)
        }
    }

    /**
     * 检查是否越界及控制越界阻力系数
     */
    private fun checkOut() {
        //TODO
    }

    /**
     * 越界回弹动画
     */
    private fun startAnim(startLocation: Float, isX: Boolean) {
        val anim =
            ObjectAnimator.ofFloat(this, if (isX) "translationX" else "translationY", startLocation)
        anim.duration = 500
        anim.interpolator = OvershootInterpolator()
        anim.start()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        lifecycle?.removeObserver(this)
    }
}