package com.example.libHome.mavericks

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.example.libHome.data.Banner

data class TestBannerState(
    val banner: Async<TestBannerBean> = Uninitialized
): MavericksState

data class TestBannerBean(
    val data: List<Banner>
)