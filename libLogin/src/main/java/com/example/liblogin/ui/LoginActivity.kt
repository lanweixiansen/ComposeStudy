package com.example.liblogin.ui

import com.therouter.router.Route
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.liblogin.databinding.LoginActivityLoginBinding

@Route(path = RouteConsts.LOGIN_LOGIN)
class LoginActivity : BaseActivity<LoginActivityLoginBinding>() {
    override fun initView() {

    }

    override fun initDate() {

    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}