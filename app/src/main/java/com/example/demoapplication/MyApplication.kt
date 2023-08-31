package com.example.demoapplication

import android.app.Application
import com.example.demoapplication.appTask.initMMKV
import com.example.lib_base.manager.AppManager
import io.flutter.embedding.engine.FlutterEngineGroup

class MyApplication : Application() {
    lateinit var engines: FlutterEngineGroup

    override fun onCreate() {
        super.onCreate()
        AppManager.setTime1(System.currentTimeMillis())
        engines = FlutterEngineGroup(this)
    }
}