package com.example.liblogin.ui

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.SpanUtils
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.toast
import com.example.lib_base.utils.RouteConsts
import com.example.liblogin.LoginViewModel
import com.example.liblogin.R
import com.example.liblogin.databinding.LoginActivityLoginBinding
import com.therouter.router.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Route(path = RouteConsts.LOGIN_LOGIN)
class LoginActivity : BaseActivity<LoginActivityLoginBinding>(), MotionLayout.TransitionListener {
    private val mViewModel by viewModels<LoginViewModel>()
    override fun initView() {
        mBinding.motionParent.setTransitionListener(this)
    }

    override fun initDate() {
        SpanUtils.with(mBinding.tvPrivacy)
            .append("我已阅读并同意").append("《用户协议》").setBold()
            .setForegroundColor(ContextCompat.getColor(this, com.example.lib_base.R.color.primary))
            .setClickSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    "用户协议".toast()
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }
            })
            .append("和").append("《隐私政策》").setBold()
            .setForegroundColor(ContextCompat.getColor(this, com.example.lib_base.R.color.primary))
            .setClickSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    "隐私政策".toast()
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }
            })
            .create()
        mBinding.tvPrivacy.highlightColor =
            ContextCompat.getColor(this, com.example.uilibrary.R.color.color_translation)
    }

    override fun initListener() {
        super.initListener()
        mBinding.btnClose.setOnClickListener {
            finishAfterTransition()
        }
    }

    override fun initObserver() {
        super.initObserver()
        mViewModel.loginSuccess.observe(this) {
            finishAfterTransition()
        }
        mViewModel.loginError.observe(this) {
            mBinding.motionParent.enableTransition(R.id.login_transition, false)
            mBinding.motionParent.transitionToStart()
        }
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        if (p3 == 0f) {
            mBinding.motionParent.enableTransition(R.id.login_transition, true)
        }
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        if (p1 == R.id.end) {
            lifecycleScope.launch {
                delay(500)
                mViewModel.login(
                    mBinding.etAccount.text.toString(),
                    mBinding.etPassword.text.toString(),
                    mBinding.checkedPrivacy.isChecked
                )
            }
        }
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
}