package com.example.demoapplication

import android.app.Application
import com.example.demoapplication.appTask.initMMKV
import io.flutter.embedding.engine.FlutterEngineGroup

class MyApplication : Application() {
    lateinit var engines: FlutterEngineGroup

    override fun onCreate() {
        super.onCreate()
        engines = FlutterEngineGroup(this)
        initMMKV(this)
    }
}