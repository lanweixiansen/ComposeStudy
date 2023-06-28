package com.example.demoapplication.appTask

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.lib_base.ext.isEqualType
import com.example.lib_base.ext.showPrivacyDialog
import com.example.lib_base.ext.toast
import com.example.lib_base.manager.AppManager
import com.example.uilibrary.widget.CustomRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.therouter.TheRouter
import com.therouter.app.flowtask.lifecycle.FlowTask
import com.therouter.flow.TheRouterFlowTask

/**
 * TheRouter业务节点订阅（自动初始化功能）
 */
object ApplicationTask {

    private const val CHECK_PRIVACY = "check_privacy"

    fun checkPrivacy(context: Context, onSuccess: (() -> Unit)? = null) {
        if (context.isEqualType<Activity>()) {
            if (!AppManager.isAgreePrivacy()) {
                showPrivacyDialog(context) {
                    TheRouter.runTask(CHECK_PRIVACY)
                    onSuccess?.invoke()
                }
                return
            }
        } else if (AppManager.isAgreePrivacy()) {
            TheRouter.runTask(CHECK_PRIVACY)
        }
    }

    /**
     * 模拟第三方SDK初始化
     * 当前只有同意隐私协议和 appManager 初始化完成之后才会初始化mmkv
     */
    @FlowTask("init_mmkv", dependsOn = "$CHECK_PRIVACY,init_app_manager")
    @JvmStatic
    fun initMMKV(context: Context) {
        "MMKV初始化完成".toast()
    }

    /**
     * 初始化APP管理类
     */
    @FlowTask("init_app_manager", dependsOn = TheRouterFlowTask.APP_ONSPLASH)
    @JvmStatic
    fun initAppManager(context: Context) {
        AppManager.init(context as Application)
    }

    /**
     * SmartRefresh 添加默认头部加载更多
     */
    @FlowTask("init_smart_refresh", dependsOn = TheRouterFlowTask.APP_ONSPLASH)
    @JvmStatic
    fun initSmartRefresh(context: Context) {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { ctx, _ ->
            CustomRefreshHeader(ctx)
        }
    }
}