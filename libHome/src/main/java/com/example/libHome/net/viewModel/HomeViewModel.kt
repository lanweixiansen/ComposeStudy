package com.example.libHome.net.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.libHome.data.Banner
import com.example.libHome.net.repository.BannerRepository
import com.example.lib_base.ext.toast
import com.example.libnet.room.dataBase.HomeBannerDataBase
import com.example.libnet.room.entry.HomeBannerEntry
import com.example.libnet.viewModel.BaseViewModel
import com.example.libnet.viewModel.onComplete
import com.example.libnet.viewModel.onError
import com.example.libnet.viewModel.onSuccess
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    private val repository = BannerRepository()

    val mBanner: MutableLiveData<MutableList<Banner>> = MutableLiveData()
    val mDBBanner: LiveData<List<HomeBannerEntry>> =
        HomeBannerDataBase.newInstance().getBannerDao().getPlants()


    fun getBanner() {
        netRequest(action = { repository.mHomeApi.getHomeBanner() }) {
            onSuccess {
                "加载成功".toast()
                mBanner.value = it
            }
            onError {
                "加载失败".toast()
            }
            onComplete {
                "加载完成".toast()
            }
        }
    }

    fun getBannerByDB() {
        viewModelScope.launch {
            repository.getBanner()
        }
    }

}
