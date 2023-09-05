package com.example.uilibrary.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import com.example.uilibrary.uiUtils.dp2px
import kotlin.math.abs

class SlidingSuspensionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), DefaultLifecycleObserver {
    private var mOldX = 0f
    private var mOldY = 0f
    private var mOldRawX = 0f
    private var mOldRawY = 0f
    private var mOldTranslationX = 0f
    private var mOldTranslationY = 0f
    private var mMaxWidth = 0
    private var mMaxHeight = 0
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
                showAnim(event.rawX, event.rawY)
            }
        }
        return true
    }

    private fun showAnim(rawX: Float, rawY: Float) {
        if (rawX <= mMaxWidth / 2) {
            startAnim(-(this.left - dp2px(8f)), true)
        } else {
            startAnim(mMaxWidth - this.right - dp2px(8f), true)
        }
        if (rawY >= mMaxHeight - dp2px(8f) - this.height) {
            startAnim(mMaxHeight - this.bottom - dp2px(8f), false)
        } else if (rawY <= dp2px(8f) + this.height) {
            startAnim(-(this.top - dp2px(8f)), false)
        }
    }

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

    private inline fun <reified T> Any.saveAs(): T {
        return this as T
    }

    private inline fun <reified T> Any.isEqualType(): Boolean {
        return this is T
    }
}