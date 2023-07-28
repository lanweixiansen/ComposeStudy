package com.example.lib_me

import android.os.Bundle
import com.blankj.utilcode.util.FileUtils
import com.example.lib_base.BuildConfig
import com.example.lib_base.ext.toast
import com.example.lib_base.manager.AppManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel

class MyFlutterActivity : FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        withNewEngine().initialRoute(intent.getStringExtra("route").toString())
        val channel = flutterEngine?.dartExecutor?.let {
            MethodChannel(
                it,
                "dev.flutter.example/route"
            )
        }
        channel?.setMethodCallHandler { call, result ->
            val toast: String = call.argument<String>("toast").toString()
            when (call.method) {
                "showToast" -> toast.toast()
                "onBackPress" -> this.onBackPressed()
                // 退出登陆
                "logout" -> {
                    AppManager.goLogin()
                    finish()
                }
                // 获取缓存大小
                "getCacheSize" -> {
                    result.success(FileUtils.getSize(cacheDir))
                }
                // 清除缓存
                "clearCacheSize" -> {
                    FileUtils.deleteAllInDir(cacheDir)
                    FileUtils.deleteAllInDir(externalCacheDir)
                    "缓存清理完成".toast()
                }
                // 版本号
                "version_name" -> {
                    result.success("v.${BuildConfig.VERSION_NAME}.${BuildConfig.VERSION_CODE}")
                }
            }
        }
    }
}