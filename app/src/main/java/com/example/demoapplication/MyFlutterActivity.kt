package com.example.demoapplication

import android.content.Context
import android.os.Bundle
import com.blankj.utilcode.util.FileUtils
import com.example.demoapplication.appTask.EngineBindings
import com.example.lib_base.BuildConfig
import com.example.lib_base.ext.toast
import com.example.lib_base.manager.AppManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine

class MyFlutterActivity : FlutterActivity() {
    private lateinit var mFlutterEngine: EngineBindings

    override fun provideFlutterEngine(context: Context): FlutterEngine? {
        val route = intent.getStringExtra("route").toString()
        mFlutterEngine = EngineBindings(this, route)
        return mFlutterEngine.engine
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val channel = mFlutterEngine.channel
        channel.setMethodCallHandler { call, result ->
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

    override fun onDestroy() {
        super.onDestroy()
        mFlutterEngine.detach()
    }
}