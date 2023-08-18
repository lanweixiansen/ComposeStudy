package com.example.libHome.data

import com.example.lib_base.utils.RouteConsts

object AnimBean {
    data class AnimData(
        val title: String,
        val route: String? = "",
    )

    val itemData = listOf(
        AnimData("属性动画", RouteConsts.NEWS_ROUTE_OBJECT_ANIM_FRAGMENT),
        AnimData("文字变化", RouteConsts.NEWS_ROUTE_SCROLL_TEXT_FRAGMENT),
        AnimData(""),
        AnimData(""),
        AnimData(""),
        AnimData(""),
        AnimData(""),
        AnimData(""),
    )
}