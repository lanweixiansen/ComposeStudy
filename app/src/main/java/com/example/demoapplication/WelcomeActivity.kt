package com.example.demoapplication

import android.content.Intent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.demoapplication.appTask.AGREE_PRIVACY
import com.example.demoapplication.databinding.ActivityWelcomeBinding
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.showPrivacyDialog
import com.example.lib_base.manager.AppData
import com.therouter.TheRouter

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {
    override fun beforeOnCreated() {
        super.beforeOnCreated()
        installSplashScreen()
    }

    override fun initView() {
        if (!AppData.isAgreePrivacy()) {
            showPrivacyDialog(this,
                onSuccess = {
                    TheRouter.runTask(AGREE_PRIVACY)
                    goMain()
                }, onRefuse = {
                    finish()
                })
        } else {
            goMain()
        }
    }

    private fun goMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun initDate() {

    }

    override fun addTopMargin() = false

}