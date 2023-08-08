package com.example.libHome.span

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.MetricAffectingSpan
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import java.util.LinkedList

class TextSpanUtils(private val textView: TextView) {
    private val spans: LinkedList<MetricAffectingSpan> = LinkedList()
    private var content =
        "Android是一种基于Linux的自由及开放源代码的操作系统，主要使用于移动设备，如智能手机和平板电脑，由Google公司和开放手机联盟领导及开发。尚未有统一中文名称，中国大陆地区较多人使用“安卓”或“安致”。"
    private val stringBuilder = StringBuilder()

    fun addIconTextSpan(
        context: Context,
        @ColorRes color: Int,
        content: String,
        marginRightDp: Int = 2
    ): TextSpanUtils {
        stringBuilder.append(";")
        val topSpan = IconTextSpan(context, color, content)
        topSpan.setRightMarginDpValue(marginRightDp)
        spans.add(topSpan)
        return this
    }

    fun addImgSpan(context: Context, @DrawableRes img: Int): TextSpanUtils {
        stringBuilder.append(";")
        val imgSpan = ImgSpan(context, img)
        imgSpan.setImageSize(36, 13, 5)
        spans.add(imgSpan)
        return this
    }

    fun setContent(content: String): TextSpanUtils {
        this.content = content
        return this
    }

    fun created(needContent: Boolean = true) {
        if (needContent)
            stringBuilder.append(content)
        val spannableString = SpannableString(stringBuilder.toString())
        //循环遍历设置Span
        for (i in spans.indices) {
            spannableString.setSpan(spans[i], i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textView.text = spannableString
    }
}

fun TextView.withTextSpan(): TextSpanUtils {
    return TextSpanUtils(this)
}