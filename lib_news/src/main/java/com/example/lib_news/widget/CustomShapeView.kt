package com.example.lib_news.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.example.lib_base.utils.dp2px


class CustomShapeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var paint: Paint = Paint()
    private var path: Path
    private var shapeRect: RectF
    private val topLeftCornerRadius = dp2px(2f) // 左上角圆角半径

    private val topRightCornerRadius = dp2px(13f) // 右上角圆角半径

    private val bottomLeftCornerRadius = dp2px(2f) // 左下角圆角半径

    private val bottomRightCornerRadius = dp2px(13f) // 右下角圆角半径

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        path = Path()
        shapeRect = RectF()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

        paint.shader = LinearGradient(
            0f,
            height / 2f,
            width.toFloat(),
            height / 2f,
            Color.parseColor("#80FF356C"),
            Color.parseColor("#80DB25E1"),
            Shader.TileMode.CLAMP
        )
        val width = width
        val height = height
        path.reset()

        // 左上角圆角
        shapeRect[dp2px(10f), 0f, 2 * topLeftCornerRadius + dp2px(10f)] = 2 * topLeftCornerRadius
        path.arcTo(shapeRect, 180f, 90f)

        // 右上角圆角
        shapeRect[width - 2 * topRightCornerRadius, 0f, width.toFloat()] =
            2 * topRightCornerRadius
        path.arcTo(shapeRect, 270f, 90f)

        // 右下角圆角
        shapeRect[width - 2 * bottomRightCornerRadius, height - 2 * bottomRightCornerRadius, width.toFloat()] =
            height.toFloat()
        path.arcTo(shapeRect, 0f, 90f)

        // 左下角圆角
        shapeRect[0f, height - 2 * bottomLeftCornerRadius, 2 * bottomLeftCornerRadius] =
            height.toFloat()
        path.arcTo(shapeRect, 90f, 90f)

        // 绘制梯形
        path.lineTo(topLeftCornerRadius / 2 + dp2px(10f), 0f)
        canvas.drawPath(path, paint)
        super.onDraw(canvas)
    }
}
