package com.example.libHome.mavericks

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.example.libHome.data.Banner

/**
 * MavericksNetFragment 页面State，储存页面所有的数据及状态
 */
data class MavericksPageState(
    val banner: Async<TestBannerBean> = Uninitialized,
    val isChecked: Boolean = false
) : MavericksState

data class TestBannerBean(
    val data: List<Banner>
)