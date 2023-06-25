package com.example.demoapplication

import android.content.Intent
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.demoapplication.databinding.ActivitySplashBinding
import com.example.lib_base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initView() {
        lifecycleScope.launch {
            delay(2_000L)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
            mBinding.lottie.cancelAnimation()
        }
    }

    override fun initDate() {}

    override fun setWindowStyle(window: Window) {
        super.setWindowStyle(window)
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }
}