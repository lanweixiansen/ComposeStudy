package com.example.liblogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lib_base.ext.toast
import com.example.lib_base.manager.AppData
import com.example.libnet.viewModel.BaseViewModel

class LoginViewModel : BaseViewModel() {
    private val _loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val loginSuccess: LiveData<Boolean> = _loginSuccess
    val loginError: MutableLiveData<Boolean> = MutableLiveData()

    fun login(phone: String?, password: String?) {
        checkLogin(phone, password) {
            loginError.value = true
            return
        }
        _loginSuccess.value = true
        "登录成功".toast()
        AppData.saveToken("token")
    }

    private inline fun checkLogin(phone: String?, password: String?, checkError: () -> Unit) {
        if (phone.isNullOrBlank()) {
            "请输入手机号".toast()
            checkError()
        } else if (password.isNullOrBlank()) {
            "请输入密码".toast()
            checkError()
        }
    }

}