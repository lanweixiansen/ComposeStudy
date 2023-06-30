package com.example.demoapplication

import android.content.Intent
import com.example.demoapplication.appTask.ApplicationTask.AGREE_PRIVACY
import com.example.demoapplication.databinding.ActivitySplashBinding
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.showPrivacyDialog
import com.example.lib_base.manager.AppData
import com.therouter.TheRouter

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initView() {
        checkPrivacy()
//            mBinding.lottie.cancelAnimation()
    }

    private fun checkPrivacy() {
        if (AppData.isAgreePrivacy()) {
            gotoMain()
        } else {
            showPrivacyDialog(this) {
                TheRouter.runTask(AGREE_PRIVACY)
                gotoMain()
            }
        }
    }

    private fun gotoMain() {
        startActivity(Intent(this@SplashActivity, NewMainActivity::class.java))
        finish()
    }

    override fun initDate() {}
}