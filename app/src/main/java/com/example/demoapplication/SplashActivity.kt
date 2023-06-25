package com.example.demoapplication

import android.content.Intent
import android.view.Window
import androidx.core.view.WindowCompat
import com.example.demoapplication.databinding.ActivitySplashBinding
import com.example.lib_base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initView() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
//            mBinding.lottie.cancelAnimation()
    }

    override fun initDate() {}

    override fun setWindowStyle(window: Window) {
        super.setWindowStyle(window)
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }
}