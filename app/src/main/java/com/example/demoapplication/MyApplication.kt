package com.example.demoapplication

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import com.example.lib_base.manager.AppManager
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

        val screenCaptureCallback2 = if (Build.VERSION.SDK_INT >= 34) {
            object : Activity.ScreenCaptureCallback {
                override fun onScreenCaptured() {

                }
            }
        } else {
            null
        }


        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {
                if (Build.VERSION.SDK_INT >= 34) {
                    if (screenCaptureCallback2 != null) {
                        activity.registerScreenCaptureCallback(mainExecutor,
                            screenCaptureCallback2
                        )
                    }
                }

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
               val header =  activity.javaClass.getDeclaredField("mScreenCaptureCallbackHandler")
                header.isAccessible = true
                ( header as ScreenCaptureCallbackHandler)


                if (Build.VERSION.SDK_INT >= 34) {
                    screenCaptureCallback2?.let { activity.unregisterScreenCaptureCallback(it) }
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

        })


    }
}