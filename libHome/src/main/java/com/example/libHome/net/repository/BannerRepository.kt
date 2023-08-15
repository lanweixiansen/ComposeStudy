package com.example.libHome.net.repository

import com.example.libHome.data.Banner
import com.example.libHome.net.HomeApi
import com.example.libnet.manager.HttpManager
import com.example.libnet.room.dataBase.HomeBannerDataBase
import com.example.libnet.room.entry.HomeBannerEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BannerRepository {
    val mHomeApi by lazy { HttpManager.created(HomeApi::class.java) }

    suspend fun getBanner() {
        translationBanner(getBannerList()).also {
            if (!it.isNullOrEmpty()) {
                HomeBannerDataBase.newInstance().getBannerDao().insertAll(it)
            }
        }
    }

    private suspend fun getBannerList(): List<Banner>? {
        return withContext(Dispatchers.IO) {
            mHomeApi.getHomeBanner().data
        }
    }

    private fun translationBanner(list: List<Banner>?): List<HomeBannerEntry>? {
        return list?.mapIndexed { index, banner ->
            HomeBannerEntry(index.toLong(), banner.imagePath, banner.desc)
        }
    }

}