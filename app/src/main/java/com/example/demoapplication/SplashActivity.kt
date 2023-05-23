package com.example.demoapplication

import android.animation.Animator
import android.content.Intent
import com.example.demoapplication.databinding.ActivitySplashBinding
import com.example.lib_base.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun initView() {
        mBinding.lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}

        })
    }

    override fun initDate() {

    }
}