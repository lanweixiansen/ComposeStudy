package com.example.liblogin.ui

import androidx.activity.viewModels
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.liblogin.LoginViewModel
import com.example.liblogin.databinding.LoginActivityLoginBinding
import com.therouter.TheRouter
import com.therouter.router.Route

@Route(path = RouteConsts.LOGIN_LOGIN)
class LoginActivity : BaseActivity<LoginActivityLoginBinding>() {
    private val mViewModel by viewModels<LoginViewModel>()
    override fun initView() {}

    override fun initDate() {}

    override fun initListener() {
        super.initListener()
        mBinding.btnClose.setOnClickListener {
            finishAfterTransition()
        }
        mBinding.btnLogin.setOnClickListener {
            mViewModel.login("12", "12")
        }
    }

    override fun initObserver() {
        super.initObserver()
        mViewModel.loginSuccess.observe(this) {
            TheRouter.build("/app/NewMainActivity").navigation()
        }
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}