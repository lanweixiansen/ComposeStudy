package com.example.lib_base.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.lib_base.GlideApp
import java.io.File

/**
 * 图片加载方法
 */

inline fun Context.checkContext(checkError: () -> Unit) {
    if (this is Activity) {
        if (this.isFinishing || this.isDestroyed) {
            checkError()
        }
    }
}

fun ImageView.loadImage(url: String?) {
    this.context.checkContext { return }
    GlideApp.with(this).load(url).centerCrop().into(this)
}

fun ImageView.loadImage(@DrawableRes drawable: Int) {
    this.context.checkContext { return }
    GlideApp.with(this).load(drawable).centerCrop().into(this)
}

fun ImageView.loadImage(bitmap: Bitmap) {
    this.context.checkContext { return }
    GlideApp.with(this).load(bitmap).centerCrop().into(this)
}

fun ImageView.loadImage(uri: Uri) {
    this.context.checkContext { return }
    GlideApp.with(this).load(uri).centerCrop().into(this)
}

fun ImageView.loadImage(file: File) {
    this.context.checkContext { return }
    GlideApp.with(this).load(file).centerCrop().into(this)
}