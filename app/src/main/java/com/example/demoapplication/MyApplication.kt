package com.example.demoapplication

import android.app.Application
import androidx.work.WorkManager
import com.example.demoapplication.appTask.initMMKV
import com.example.libHome.workmanager.WORK_USER_TAG
import com.example.lib_base.manager.AppManager
import com.petterp.floatingx.FloatingX
import io.flutter.embedding.engine.FlutterEngineGroup

class MyApplication : Application() {
    lateinit var engines: FlutterEngineGroup

    override fun onCreate() {
        super.onCreate()
        AppManager.setTime1(System.currentTimeMillis())
        engines = FlutterEngineGroup(this)
//        FloatingX.install {
//            setContext(this@MyApplication)
//            setLayout(com.example.lib_news.R.layout.news_test)
//            enableFx()
//        }
    }
}