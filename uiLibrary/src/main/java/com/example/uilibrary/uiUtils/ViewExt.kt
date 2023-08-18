package com.example.uilibrary.uiUtils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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

inline fun <reified T : ViewBinding> ViewGroup.viewBinding() = ViewBindingDelegate(T::class.java, this)

class ViewBindingDelegate<T : ViewBinding>(
    private val bindingClass: Class<T>,
    val fragment: ViewGroup
) : ReadOnlyProperty<ViewGroup, T> {
    private var binding: T? = null

    override fun getValue(thisRef: ViewGroup, property: KProperty<*>): T {
        binding?.let { return it }

        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java)
        @Suppress("UNCHECKED_CAST")
        binding = inflateMethod.invoke(null, LayoutInflater.from(thisRef.context), thisRef) as T
        return binding!!
    }
}