package com.example.libHome.data

import com.example.lib_base.utils.RouteConsts

object AnimBean {
    data class AnimData(
        val title: String,
        val route: String? = "",
    )

    val animItemData = listOf(
        AnimData("属性动画", RouteConsts.NEWS_ROUTE_OBJECT_ANIM_FRAGMENT),
        AnimData("文字变化", RouteConsts.NEWS_ROUTE_SCROLL_TEXT_FRAGMENT),
        AnimData("微信表情动画", RouteConsts.NEWS_ROUTE_WX_EMOJI_ACTIVITY),
        AnimData("网易云歌单管理", RouteConsts.NEWS_ROUTE_WY_MANAGER_ACTIVITY),
        AnimData(""),
        AnimData(""),
        AnimData(""),
        AnimData(""),
    )
}