package com.example.lib_base.ext

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.DrawableRes
import com.example.lib_base.manager.AppData

fun showMaterialDialog(
    context: Context,
    title: String? = "",
    message: String? = "",
    leftBtnTest: String? = "",
    rightBtnTest: String? = "",
    @DrawableRes icon: Int = 0,
    onLeftClick: ((DialogInterface) -> Unit)? = null,
    onRightClick: ((DialogInterface) -> Unit)? = null
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
        setCancelable(false)
        show()
    }
}

fun showPrivacyDialog(context: Context, onSuccess: () -> Unit, onRefuse: () -> Unit) {
    showMaterialDialog(
        context,
        title = "用户隐私政策",
        message = "是否同意用户协议与隐私政策",
        leftBtnTest = "退出APP",
        rightBtnTest = "同意",
        onRightClick = {
            onSuccess()
            AppData.agreePrivacy()
        },
        onLeftClick = {
            onRefuse.invoke()
            it.dismiss()
        }
    )
}



