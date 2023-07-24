package com.example.lib_news.data

import com.example.lib_base.utils.RouteConsts

object AnimBean {
    data class AnimData(
        val title: String,
        val route: String? = "",
    )

    val itemData = listOf(
        AnimData("属性动画", RouteConsts.NEWS_ROUTE_OBJECT_ANIM_FRAGMENT),
        AnimData("属性动画"),
        AnimData(""),
        AnimData(""),
        AnimData(""),
        AnimData(""),
        AnimData(""),
        AnimData(""),
    )
}