package com.example.libHome.net.repository

import com.example.libHome.data.Banner
import com.example.libHome.net.HomeApi
import com.example.libnet.manager.HttpManager
import com.example.libnet.room.dataBase.HomeBannerDataBase
import com.example.libnet.room.entry.HomeBannerEntry

class BannerRepository : BaseRepository() {
    val mHomeApi by lazy { HttpManager.created(HomeApi::class.java) }

    suspend fun getBanner() {
        translationBanner(getBannerList()).also {
            if (it.isNotEmpty()) {
                HomeBannerDataBase.newInstance().getBannerDao().insertAll(it)
            }
        }
    }

    private suspend fun getBannerList(): MutableList<Banner> {
        var list = mutableListOf<Banner>()
        kotlin.runCatching {
            mHomeApi.getHomeBanner().data
        }.onSuccess {
            if (it != null) {
                list = it
            }
        }
        return list
    }

    private fun translationBanner(list: List<Banner>): List<HomeBannerEntry> {
        return list.mapIndexed { index, banner ->
            HomeBannerEntry(index.toLong(), banner.imagePath, banner.desc)
        }
    }

}

open class BaseRepository {}