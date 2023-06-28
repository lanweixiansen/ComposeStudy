package com.example.demoapplication

import android.app.Application
import com.example.demoapplication.appTask.ApplicationTask

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initSdk()
    }

    private fun initSdk() {
        ApplicationTask.checkPrivacy(this)
    }
}