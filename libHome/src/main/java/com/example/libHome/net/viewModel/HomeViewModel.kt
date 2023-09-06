package com.example.libHome.net.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.libHome.data.AnimBean
import com.example.libHome.data.Banner
import com.example.libHome.data.ItemData
import com.example.libHome.data.itemData
import com.example.libHome.net.repository.BannerRepository
import com.example.lib_base.ext.toast
import com.example.libnet.room.dataBase.HomeBannerDataBase
import com.example.libnet.room.entry.HomeBannerEntry
import com.example.libnet.viewModel.BaseViewModel
import com.example.libnet.viewModel.onError
import com.example.libnet.viewModel.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    private val repository = BannerRepository()
    val mBanner: MutableLiveData<List<Banner>> = MutableLiveData()
    val mDBBanner: LiveData<List<HomeBannerEntry>> =
        HomeBannerDataBase.newInstance().getBannerDao().getPlants()
    val mItemData1 = MutableLiveData<List<ItemData>>()
    val mItemData2 = MutableLiveData<List<AnimBean.AnimData>>()

    fun getBanner() {
        netRequest(action = { repository.mHomeApi.getHomeBanner() }) {
            onSuccess {
                mBanner.value = it
            }
            onError {
                "加载失败".toast()
            }
        }
    }

    fun getBannerByDB() {
        viewModelScope.launch {
            repository.getBanner()
        }
    }

    fun getItemData(position: Int) {
        viewModelScope.launch {
            delay(100)
            if (position == 0) {
                mItemData1.value = itemData
            } else {
                mItemData2.value = AnimBean.animItemData
            }
        }
    }

}
