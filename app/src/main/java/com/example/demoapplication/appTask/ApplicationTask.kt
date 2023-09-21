package com.example.demoapplication.appTask

import android.app.Application
import android.content.Context
import com.airbnb.mvrx.Mavericks
import com.example.libHome.therouter.RouterInterceptor
import com.example.lib_base.manager.AppData
import com.example.lib_base.manager.AppManager
import com.example.uilibrary.widget.CustomRefreshHeader
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.therouter.TheRouter
import com.therouter.app.flowtask.lifecycle.FlowTask
import com.therouter.flow.TheRouterFlowTask
import io.flutter.embedding.engine.FlutterEngineCache

/**
 * TheRouter业务节点订阅（自动初始化功能）
 */

const val AGREE_PRIVACY = "agree_privacy"

/**
 * MMKV初始化完成之后检查是否同意隐私协议，同意之后初始化隐私相关SDK
 */
@FlowTask(taskName = "check_privacy", dependsOn = "init_mmkv")
fun checkPrivacy(context: Context) {
    if (AppData.isAgreePrivacy()) {
        TheRouter.runTask(AGREE_PRIVACY)
    }
}

/**
 * 模拟需要同意隐私协议才能初始化的SDK
 */
@FlowTask(taskName = "init_privacy_sdk", dependsOn = AGREE_PRIVACY)
fun initPrivacySdk(context: Context) {

}

/**
 * 模拟不需要同意隐私协议的SDK初始化
 */
@FlowTask("init_no_privacy_sdk", dependsOn = TheRouterFlowTask.APP_ONSPLASH)
fun initNoPrivacySdk(context: Context) {
    AppManager.init(context as Application)
    SmartRefreshLayout.setDefaultRefreshHeaderCreator { ctx, _ ->
        CustomRefreshHeader(ctx)
    }
    SmartRefreshLayout.setDefaultRefreshFooterCreator { ctx, _ ->
        ClassicsFooter(ctx).setDrawableSize(20f)
    }
    Mavericks.initialize(context)
}

/**
 * 初始化mmkv
 */
@FlowTask("init_mmkv", async = true)
fun initMMKV(context: Context) {
    MMKV.initialize(context)
}

/**
 * 初始化TheRouter拦截器
 */
@FlowTask("init_interceptor", dependsOn = TheRouterFlowTask.APP_ONSPLASH)
fun initInterceptor(context: Context) {
    RouterInterceptor.addLoginInterceptor()
    RouterInterceptor.addRouterInterceptor()
}

/**
 * 初始化Flutter引擎
 */
fun initFlutterChannel(topBindings: EngineBindings) {
    if (FlutterEngineCache.getInstance().get("FlutterEngin") == null) {
        FlutterEngineCache.getInstance().put("FlutterEngin", topBindings.engine)
    }
}
