package com.example.libHome.data

import com.example.lib_base.utils.RouteConsts

data class ItemData(
    val content: String,
    val route: String = ""
)

val itemData = listOf(
    ItemData("自定义LinearLayout添加隐藏Fragment", RouteConsts.HOME_LINEARLAYOUT),
    ItemData("EventBus接受数据顺序", RouteConsts.HOME_EVENT_BUS_ACTIVITY),
    ItemData("ARouter相关", RouteConsts.HOME_ROUTER_ACTIVITY),
    ItemData("Compose & ViewModel"),
    ItemData("Intent实现转场动画"),
    ItemData("Compose基本布局"),
    ItemData("Compose列表展示，图片加载"),
    ItemData("Compose & ViewModel"),
    ItemData("Intent实现转场动画"),
    ItemData("Compose基本布局"),
    ItemData("Compose列表展示，图片加载"),
    ItemData("Compose & ViewModel"),
    ItemData("Intent实现转场动画"),
    ItemData("Compose基本布局"),
    ItemData("Compose列表展示，图片加载"),
    ItemData("Compose & ViewModel"),
    ItemData("Intent实现转场动画"),
    ItemData("Compose基本布局"),
    ItemData("Compose列表展示，图片加载"),
    ItemData("Compose & ViewModel"),
    ItemData("Intent实现转场动画"),
    ItemData("Compose基本布局"),
    ItemData("Compose列表展示，图片加载"),
    ItemData("Compose & ViewModel"),
    ItemData("Intent实现转场动画"),
)