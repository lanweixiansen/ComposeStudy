package com.example.lib_base.ext

import android.content.Context
import android.widget.Toast
import com.example.lib_base.manager.AppManager

inline fun <reified T> Any.saveAs(): T {
    return this as T
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.saveAsUnChecked(): T {
    return this as T
}

inline fun <reified T> Any.isEqualType(): Boolean {
    return this is T
}

fun Any.toast(context: Context? = null) {
    Toast.makeText(AppManager.getContext(), this.toString(), Toast.LENGTH_SHORT).show()
}

fun String.toast() {
    Toast.makeText(AppManager.getContext(), this.toString(), Toast.LENGTH_SHORT).show()
}

fun String.toExFloat(): Float {
    var float = 0f
    kotlin.runCatching {
        this.toFloat()
    }.onSuccess {
        float = it
    }
    return float
}

fun String.toExInt(): Int {
    var int = 0
    kotlin.runCatching {
        this.toInt()
    }.onSuccess {
        int = it
    }
    return int
}