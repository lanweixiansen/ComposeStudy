package com.example.libHome.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UserHeartWork(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        var timeCount = System.currentTimeMillis()
        val refreshTime = 6
        while (true) {
            if (System.currentTimeMillis() - timeCount >= refreshTime * 1000) {
                timeCount = System.currentTimeMillis()
                Log.d("WorkManager", "doWork: 用户心跳")
            }
        }
    }
}