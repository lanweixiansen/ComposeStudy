package com.example.libHome.therouter

import android.content.Context
import com.blankj.utilcode.util.ActivityUtils
import com.example.lib_base.ext.showMaterialDialog
import com.therouter.TheRouter
import com.therouter.router.Navigator
import com.therouter.router.action.interceptor.ActionInterceptor
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * 思路一：使用TheRouter提供的方法实现多个弹窗依次弹出
 */
@com.therouter.router.action.ActionInterceptor(actionName = "show_dialog")
class DialogAction1 : ActionInterceptor() {
    override fun handle(context: Context, navigator: Navigator): Boolean {
        super.handle(context, navigator)
        showMaterialDialog(
            ActivityUtils.getTopActivity(),
            "弹窗1",
            "这是弹窗1",
            leftBtnTest = "展示下一个",
            onLeftClick = { TheRouter.build("show_dialog_2").action() }
        )
        return super.handle(context, navigator)
    }

    // 当有多个Action的实现时，通过设置priority设置优先级，数字越小优先级越高
    override val priority: Int
        get() = 1
}

@com.therouter.router.action.ActionInterceptor(actionName = "show_dialog_2")
class DialogAction2 : ActionInterceptor() {
    override fun handle(context: Context, navigator: Navigator): Boolean {
        showMaterialDialog(
            ActivityUtils.getTopActivity(),
            "弹窗2",
            "这是弹窗2",
            leftBtnTest = "展示下一个",
            onLeftClick = { TheRouter.build("show_dialog_3").action() }
        )
        return super.handle(context, navigator)
    }
}

@com.therouter.router.action.ActionInterceptor(actionName = "show_dialog_3")
class DialogAction3 : ActionInterceptor() {
    override fun handle(context: Context, navigator: Navigator): Boolean {
        super.handle(context, navigator)
        showMaterialDialog(ActivityUtils.getTopActivity(), "弹窗3", "这是弹窗3", "关闭弹窗")
        return false
    }
}


/**
 * 思路二：使用协程挂起恢复的方式实现多个弹窗依次弹出
 */
suspend fun showDialog(context: Context, title: String?, message: String?, lefBtnText: String) =
    suspendCancellableCoroutine<Any> { coroutine ->
        showMaterialDialog(context = context, title, message, lefBtnText, onLeftClick = {
            coroutine.resume(Unit)
        })
    }