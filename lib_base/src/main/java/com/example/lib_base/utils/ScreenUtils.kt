package com.example.lib_base.utils

import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils

fun getScreenHeight() = ScreenUtils.getScreenHeight().toFloat()

fun getScreenWidth() = ScreenUtils.getScreenWidth().toFloat()

fun dp2px(dp: Float) = ConvertUtils.dp2px(dp).toFloat()