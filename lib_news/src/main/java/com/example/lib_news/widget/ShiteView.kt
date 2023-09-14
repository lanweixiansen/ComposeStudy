package com.example.lib_news.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import com.example.lib_news.R
import com.example.uilibrary.uiUtils.dp2px
import com.example.uilibrary.uiUtils.getScreenHeight
import com.example.uilibrary.uiUtils.getScreenWidth
import java.util.LinkedList
import kotlin.random.Random

class ShiteView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val list = LinkedList<ImageView>()

    fun beginAnim(number: Int) {
        for (i in 0 until number) {
            val view = ImageView(context).apply { setImageResource(R.mipmap.bian) }
            addView(view, getIvWidth(), getIvWidth())
            list.add(view)
        }
        list.forEach {
            it.translationX = getTransX()
            it.translationY = getTransY()
            it.rotation = getRation().toFloat()
            startAnim(it)
        }
    }

    private fun startAnim(it: ImageView) {
        val scale = getRoScale()
        val holder = PropertyValuesHolder.ofFloat("scaleX", scale)
        val holder2 = PropertyValuesHolder.ofFloat("scaleY", scale)
        val holder3 = PropertyValuesHolder.ofFloat("translationY", getTransY2())
        val holder4 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f)
        val holder5 = PropertyValuesHolder.ofFloat("translationY", dp2px(300f))
        val anim = ObjectAnimator.ofPropertyValuesHolder(it, holder2, holder, holder3).apply {
            duration = 500
            interpolator = AccelerateInterpolator()
        }
        val anim2 = ObjectAnimator.ofPropertyValuesHolder(it, holder4, holder5).apply {
            duration = 1500
            interpolator = LinearInterpolator()
        }
        val animSet = AnimatorSet()
        animSet.play(anim2).after(1000).after(anim)
        animSet.start()
        animSet.doOnEnd {
            this.removeAllViews()
            list.clear()
        }
    }

    private fun getIvWidth() = Random.nextInt(dp2px(30), dp2px(51))
    private fun getTransX(): Float = getScreenWidth() / 2 + Random.nextInt(-dp2px(100), dp2px(100))
    private fun getTransY(): Float = getScreenHeight() - Random.nextInt(dp2px(200), dp2px(400))
    private fun getTransY2(): Float = Random.nextInt(dp2px(30), dp2px(300)).toFloat()
    private fun getRation() = Random.nextInt(0, 180)

    private fun getRoScale() = Random.nextInt(1, 5).toFloat()
}