package com.example.libHome.net.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.libHome.data.Banner
import com.example.libHome.net.HomeApi
import com.example.lib_base.ext.toast
import com.example.libnet.manager.HttpManager
import com.example.libnet.viewModel.BaseViewModel
import com.example.libnet.viewModel.onComplete
import com.example.libnet.viewModel.onError
import com.example.libnet.viewModel.onSuccess

class HomeViewModel : BaseViewModel() {
    private val mHomeApi by lazy { HttpManager.created(HomeApi::class.java) }

    private val mBanner: MutableLiveData<MutableList<Banner>> = MutableLiveData()

    fun getBanner() {
        netRequest(true, action = { mHomeApi.getHomeBanner() }) {
            onSuccess {
                "加载成功".toast()
            }
            onError {
                "加载失败".toast()
            }
            onComplete {
                "加载完成".toast()
            }
        }
    }

}
