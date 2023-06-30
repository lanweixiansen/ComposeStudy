package com.example.libHome.therouter

import android.content.Context
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ThreadUtils
import com.example.lib_base.ext.showMaterialDialog
import com.therouter.router.Navigator
import com.therouter.router.action.interceptor.ActionInterceptor

@com.therouter.router.action.ActionInterceptor(actionName = "show_dialog")
class DialogAction1 : ActionInterceptor() {
    override fun handle(context: Context, navigator: Navigator): Boolean {
        super.handle(context, navigator)
        showMaterialDialog(
            ActivityUtils.getTopActivity(),
            "弹窗1",
            "这是弹窗1",
            leftBtnTest = "展示下一个",
        )
        Thread.sleep(1000)
        return true
    }

    override val priority: Int
        get() = 1
}

@com.therouter.router.action.ActionInterceptor(actionName = "show_dialog")
class DialogAction2 : ActionInterceptor() {
    override fun handle(context: Context, navigator: Navigator): Boolean {
        showMaterialDialog(
            ActivityUtils.getTopActivity(),
            "弹窗2",
            "这是弹窗2",
            leftBtnTest = "展示下一个"
        )
        return super.handle(context, navigator)
    }

    override val priority: Int
        get() = 2
}

@com.therouter.router.action.ActionInterceptor(actionName = "show_dialog")
class DialogAction3 : ActionInterceptor() {
    override fun handle(context: Context, navigator: Navigator): Boolean {
        super.handle(context, navigator)
        showMaterialDialog(ActivityUtils.getTopActivity(), "弹窗3", "这是弹窗3")
        return false
    }

    override val priority: Int
        get() = 3
}