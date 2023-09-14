package com.example.lib_news.utils

import android.animation.TypeEvaluator
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.PointF


class PathEvaluator(val path: Path): TypeEvaluator<PointF> {

    override fun evaluate(fraction: Float, startValue: PointF?, endValue: PointF?): PointF {
        val pathMeasure = PathMeasure(path, false)
        val point = FloatArray(2)
        pathMeasure.getPosTan(pathMeasure.length * fraction, point, null)
        return PointF(point[0], point[1])
    }
}