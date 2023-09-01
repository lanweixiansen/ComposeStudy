package com.example.demoapplication.appTask

import android.app.Activity
import android.content.Intent
import com.example.demoapplication.MyApplication
import com.example.demoapplication.MyFlutterActivity
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

class EngineBindings(private val activity: Activity, entrypoint: String) {
    val channel: MethodChannel
    val engine: FlutterEngine

    init {
        val app = activity.applicationContext as MyApplication
        val dartEntrypoint =
            DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(), entrypoint
            )
        engine = app.engines.createAndRunEngine(activity, dartEntrypoint)
        channel = MethodChannel(engine.dartExecutor.binaryMessenger, "dev.flutter.example/route")
    }

    fun attach() {
        channel.setMethodCallHandler { call, _ ->
            val route: String = call.argument<String>("data").toString()
            when (call.method) {
                "routeActivity" -> {
                    val intent = Intent(activity, MyFlutterActivity::class.java)
                    intent.putExtra("route", route)
                    activity.startActivity(intent)
                }
            }
        }
    }

    fun detach() {
        engine.destroy()
        channel.setMethodCallHandler(null)
    }
}
