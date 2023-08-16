package com.example.libHome.mavericks

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.example.libHome.net.repository.BannerRepository

class MavericksVM(bannerState: MavericksPageState) :
    MavericksViewModel<MavericksPageState>(bannerState) {
    private val repository = BannerRepository()

    fun getBanner() {
        withState { oldState ->
            if (oldState.banner is Loading) return@withState
            // 进行网络请求
            suspend { repository.mHomeApi.getHomeBanner2() }
                // retainValue进行赋值可以防止数据加载失败导致之前的数据被覆盖
                .execute(retainValue = MavericksPageState::banner) {
                    copy(banner = it)
                }
        }
    }

    fun changeCheckState() {
        setState {
            copy(isChecked = !isChecked)
        }
    }

}