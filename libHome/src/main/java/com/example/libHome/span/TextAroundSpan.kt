package com.example.libHome.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.style.LeadingMarginSpan.LeadingMarginSpan2
import com.example.uilibrary.uiUtils.dp2px

/**
 * @param lineCount 行数
 * @param mFirst 段落前N行margin 单位dp
 * @param mRest 段落剩余行margin 单位dp
 */
class TextAroundSpan(
    private var imgInfo: ImgInfo,
    private val lineCount: Int,
    private val mFirst: Int,
    private val mRest: Int = 0,
) : LeadingMarginSpan2 {
    /**
     * 段落缩进的行数
     */
    override fun getLeadingMarginLineCount(): Int = lineCount

    /**
     * @param first true作用于段落中前N行（N为getLeadingMarginLineCount()中返回的值），否则作用于段落剩余行
     */
    override fun getLeadingMargin(first: Boolean): Int =
        if (first) dp2px(mFirst) else dp2px(mRest)


    /**
     * 绘制页边距（leading margin）。在{@link #getLeadingMargin(boolean)}返回值调整页边距之前调用。
     */
    override fun drawLeadingMargin(
        canvas: Canvas?,
        paint: Paint?,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence?,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout?,
    ) {
        if (canvas == null || paint == null) return
        val drawable: Drawable = imgInfo.drawable
        canvas.save()
        drawable.setBounds(0, 0, dp2px(imgInfo.widthDp), dp2px(imgInfo.heightDp))
        canvas.translate(imgInfo.dx, imgInfo.dy)
        drawable.draw(canvas)
        canvas.restore()
    }

    data class ImgInfo(
        val drawable: Drawable,
        val widthDp: Int,
        val heightDp: Int,
        val dx: Float = dp2px(1).toFloat(),
        val dy: Float = dp2px(2).toFloat(),
    )
}
