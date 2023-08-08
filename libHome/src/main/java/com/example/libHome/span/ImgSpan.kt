package com.example.libHome.span

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import com.example.uilibrary.uiUtils.dp2px


class ImgSpan(context: Context, drawableResId: Int) :
    ImageSpan(context, drawableResId) {
    private var mMarginRight = dp2px(2)
    private var mImgWidth = dp2px(15)
    private var mImgHeight = dp2px(10)
    private var mDrawable: Drawable? = null
    fun setImageSize(widthDp: Int, heightDp: Int, marginRightDp: Int = 2) {
        mImgHeight = dp2px(heightDp)
        mImgWidth = dp2px(widthDp)
        mMarginRight = dp2px(marginRightDp)
        updateDrawableBounds()
    }

    private fun updateDrawableBounds() {
        mDrawable = drawable
        mDrawable?.setBounds(0, 0, mImgWidth, mImgHeight)
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return mImgWidth + mMarginRight
    }


    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        if (mDrawable != null) {
            val transY: Int = (bottom - top - mImgHeight) / 2
            canvas.save()
            canvas.translate(x, (transY + top).toFloat())
            mDrawable?.draw(canvas)
            canvas.restore()
        }
    }
}
