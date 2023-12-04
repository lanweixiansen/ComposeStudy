package com.example.uilibrary.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet

/**
 * 奇形怪状图片
 */
class RoomImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    private val rectF = RectF()
    val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 宽：64 高：60
//        rectF.set(dp2px(1.7f), dp2px(1.7f), w - dp2px(9.8f), h - dp2px(1.7f))
//        path.moveTo(width - dp2px(10f), height - dp2px(9f))
//        path.quadTo(width - dp2px(15f), dp2px(10f), width - dp2px(3.5f), dp2px(13f))
//        path.addArc(width - dp2px(24f), dp2px(1.6f), width - dp2px(3.3f), dp2px(25f), -90f, 90f)
//        path.addRect(width - dp2px(20f), dp2px(1.7f), width - dp2px(13.3f), dp2px(15f), Path.Direction.CW)
//        path.addArc(width - dp2px(25f), dp2px(1.7f), width - dp2px(4f), dp2px(25f), -90f, 180f)
//        path.addRoundRect(rectF, dp2px(9.5f), dp2px(9.5f), Path.Direction.CW)

        // 宽：72  高：72
        rectF.set(dp2px(1.7f), dp2px(1.7f), w - dp2px(11f), h - dp2px(1.7f))
        path.moveTo(w - dp2px(11f), h - dp2px(10f))
        path.quadTo(w - dp2px(15f), dp2px(-6f), w - dp2px(3.7f), dp2px(12f))
        path.addArc(w - dp2px(44f), dp2px(10.6f), w - dp2px(7.3f), dp2px(55f), -90f, 90f)
        path.addRect(w - dp2px(22f), dp2px(1.7f), w - dp2px(13.3f), dp2px(15f), Path.Direction.CW)
        path.addArc(w - dp2px(24f), dp2px(1.7f), w - dp2px(4f), dp2px(19f), -90f, 200f)
        path.addRoundRect(rectF, dp2px(10f), dp2px(10f), Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        canvas?.clipPath(path)
        super.onDraw(canvas)
        canvas?.restore()
    }
}

fun dp2px(dpValue: Float): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return (dpValue * scale + 0.5f)
}


