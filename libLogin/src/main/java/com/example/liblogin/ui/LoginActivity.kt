package com.example.liblogin.ui

import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.liblogin.databinding.LoginActivityLoginBinding
import com.therouter.TheRouter
import com.therouter.router.Route

@Route(path = RouteConsts.LOGIN_LOGIN)
class LoginActivity : BaseActivity<LoginActivityLoginBinding>() {
    override fun initView() {

    }

    override fun initDate() {

    }

    override fun initListener() {
        super.initListener()
        mBinding.btnClose.setOnClickListener {
            finishAfterTransition()
        }
        mBinding.btnLogin.setOnClickListener {
            TheRouter.build("/app/NewMainActivity").navigation()
        }
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}