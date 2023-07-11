package com.example.demoapplication

import android.os.Bundle
import com.example.lib_base.ext.toast
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
        channel?.setMethodCallHandler { call, _ ->
            val toast: String = call.argument<String>("toast").toString()
            when (call.method) {
                "showToast" -> toast.toast()
            }
        }
    }
}