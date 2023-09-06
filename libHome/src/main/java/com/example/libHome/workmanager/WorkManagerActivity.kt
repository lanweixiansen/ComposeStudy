package com.example.libHome.workmanager

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeActivityWorkManagerBinding
import com.therouter.router.Route

@Route(path = RouteConsts.HOME_ROUTER_WORK_MANAGER_ACTIVITY)
class WorkManagerActivity : BaseActivity<HomeActivityWorkManagerBinding>() {

    private val workManager = WorkManager.getInstance(this)
    override fun initView() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<UserHeartWork>()
            .setConstraints(constraints)
            .addTag(WORK_USER_TAG)
            .setInputData(workDataOf(USER_HEART_REFRESH_TIME to 60))
            .build()
        WorkManager.getInstance(this).cancelAllWorkByTag(WORK_USER_TAG)
        workManager.enqueue(workRequest)
    }

    override fun initDate() {

    }
}

const val USER_HEART_REFRESH_TIME = "user_heart_refresh_time"
const val WORK_USER_TAG = "work_user_tag"