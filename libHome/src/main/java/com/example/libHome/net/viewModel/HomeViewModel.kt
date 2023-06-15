package com.example.libHome.net.viewModel

import com.example.libHome.net.HomeApi
import com.example.lib_base.ext.toast
import com.example.libnet.manager.HttpManager
import com.example.libnet.viewModel.BaseViewModel

class HomeViewModel : BaseViewModel() {
    private val mHomeApi by lazy { HttpManager.created(HomeApi::class.java) }

    fun getBanner() {
        request(action = { mHomeApi.getHomeBanner() }, onSuccess = {
            "加载成功".toast()
        }, onComplete = {
            "加载结束".toast()
        })
    }
}
