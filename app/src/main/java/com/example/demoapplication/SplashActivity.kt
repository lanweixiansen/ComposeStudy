package com.example.demoapplication

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.example.demoapplication.databinding.ActivitySplashBinding
import com.example.lib_base.BaseActivity
import kotlinx.coroutines.delay

class SplashActivity : BaseActivity<ActivitySplashBinding>() {


    override fun initView() {
        showLoading()
        lifecycleScope.launchWhenResumed {
            delay(3_000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
            disLoading()
        }

    }

    override fun initDate() {

    }

    override fun initListener() {

    }

}