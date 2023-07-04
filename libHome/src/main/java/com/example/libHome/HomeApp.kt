package com.example.libHome

import android.app.Application
import com.example.lib_base.manager.AppManager

class HomeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppManager.init(this)
    }

}