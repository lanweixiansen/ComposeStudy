package com.example.lib_base.ext

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.DrawableRes

fun showMaterialDialog(
    context: Context,
    title: String? = "",
    message: String? = "",
    leftBtnTest: String? = "",
    rightBtnTest: String? = "",
    @DrawableRes icon: Int = 0,
    onLeftClick: ((DialogInterface) -> Unit)? = null,
    onRightClick: ((DialogInterface) -> Unit)? = null,
    isContentCenter: Boolean = true
) {
    AlertDialog.Builder(context).apply {
        if (!title.isNullOrBlank())
            setTitle(title)
        if (!message.isNullOrBlank())
            setMessage(message)
        if (!leftBtnTest.isNullOrBlank())
            setNegativeButton(leftBtnTest) { dialog, _ -> onLeftClick?.invoke(dialog) }
        if (!rightBtnTest.isNullOrBlank())
            setPositiveButton(rightBtnTest) { dialog, _ -> onRightClick?.invoke(dialog) }
        if (icon != 0)
            setIcon(icon)
        show()
    }
}

fun showPrivacyDialog(context: Context, onSuccess: () -> Unit) {
    showMaterialDialog(
        context,
        title = "用户隐私政策",
        message = "是否同意用户协议与隐私政策",
        leftBtnTest = "拒绝",
        rightBtnTest = "同意",
        onRightClick = {
            onSuccess()
        },
        onLeftClick = { it.dismiss() }
    )
}



