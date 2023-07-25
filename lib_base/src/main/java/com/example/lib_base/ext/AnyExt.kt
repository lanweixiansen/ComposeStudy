package com.example.lib_base.ext

import android.widget.Toast
import com.example.lib_base.manager.AppManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

fun String.toast() {
    CoroutineScope(Dispatchers.Main).launch {
        Toast.makeText(AppManager.getApplicationContext(), this@toast, Toast.LENGTH_SHORT).show()
    }
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