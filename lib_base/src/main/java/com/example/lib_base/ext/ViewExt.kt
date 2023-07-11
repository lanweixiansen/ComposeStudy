package com.example.lib_base.ext

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

/**
 * View 添加底部导航栏间距
 */
fun View.addMarginToNavigationBar() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemWindowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(0, 0, 0, systemWindowInsets.bottom)
        insets
    }
}

/**
 * View 添加顶部状态栏间距
 */
fun View.addMarginToEqualStatusBar() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemWindowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(0, systemWindowInsets.top, 0, 0)
        insets
    }
}