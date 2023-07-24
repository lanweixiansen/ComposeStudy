package com.example.demoapplication.appTask

import android.app.Application
import android.content.Context
import android.content.Intent
import com.example.lib_base.manager.AppData
import com.example.lib_base.manager.AppManager
import com.example.lib_me.MyFlutterActivity
import com.example.uilibrary.widget.CustomRefreshFooter
import com.example.uilibrary.widget.CustomRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.therouter.TheRouter
import com.therouter.app.flowtask.lifecycle.FlowTask
import com.therouter.flow.TheRouterFlowTask
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

/**
 * TheRouter业务节点订阅（自动初始化功能）
 */
object ApplicationTask {

    const val AGREE_PRIVACY = "agree_privacy"

    /**
     * MMKV初始化完成之后检查是否同意隐私协议，同意之后初始化隐私相关SDK
     */
    @FlowTask(taskName = "check_privacy", dependsOn = "init_mmkv")
    @JvmStatic
    fun checkPrivacy(context: Context) {
        if (AppData.isAgreePrivacy()) {
            TheRouter.runTask(AGREE_PRIVACY)
        }
    }

    /**
     * 模拟需要同意隐私协议才能初始化的SDK
     */
    @FlowTask(taskName = "init_privacy_sdk", dependsOn = AGREE_PRIVACY)
    @JvmStatic
    fun initPrivacySdk(context: Context) {

    }

    /**
     * 模拟不需要同意隐私协议的SDK初始化
     */
    @FlowTask("init_no_privacy_sdk", dependsOn = TheRouterFlowTask.APP_ONSPLASH)
    @JvmStatic
    fun initNoPrivacySdk(context: Context) {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { ctx, _ ->
            CustomRefreshHeader(ctx)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { ctx, _ ->
            CustomRefreshFooter(context = ctx)
        }
    }

    /**
     * 初始化MMKV
     */
    @FlowTask("init_mmkv", dependsOn = TheRouterFlowTask.APP_ONSPLASH)
    @JvmStatic
    fun initMMKV(context: Context) {
        MMKV.initialize(context)
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
     * Flutter引擎初始化
     */
    @FlowTask("init_flutter", dependsOn = TheRouterFlowTask.APP_ONSPLASH)
    @JvmStatic
    fun initFlutterEngin(context: Context) {
        val flutterEngine = FlutterEngine(context)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache
            .getInstance()
            .put("my_engine_id", flutterEngine)
    }


    fun initFlutterChannel(context: Context) {
        val flutterEngine = FlutterEngineCache.getInstance().get("my_engine_id")
        val channel = flutterEngine?.dartExecutor?.let {
            MethodChannel(
                it,
                "dev.flutter.example/route"
            )
        }
        channel?.setMethodCallHandler { call, _ ->
            val route: String = call.argument<String>("data").toString()
            when (call.method) {
                "routeActivity" -> {
                    val intent = Intent(context, MyFlutterActivity::class.java)
                    intent.putExtra("route", route)
                    context.startActivity(intent)
                }
            }
        }
    }
}