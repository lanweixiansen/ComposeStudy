package com.example.demoapplication

import android.content.Intent
import com.example.demoapplication.appTask.ApplicationTask
import com.example.demoapplication.databinding.ActivitySplashBinding
import com.example.lib_base.BaseActivity
import com.example.lib_base.manager.AppManager

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initView() {
        if (AppManager.isAgreePrivacy()) {
            gotoMain()
        } else {
            ApplicationTask.checkPrivacy(this) {
                gotoMain()
            }
        }
//            mBinding.lottie.cancelAnimation()
    }

    private fun gotoMain() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun initDate() {}
}