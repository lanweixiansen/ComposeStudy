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
    ItemData("viewStub延迟加载布局实现秒开", RouteConsts.HOME_ROUTER_VIEW_STUB_ACTIVITY),
    ItemData("Room & net实现数据持久化", RouteConsts.HOME_ROUTER_ROOM_ACTIVITY),
    ItemData("跳转并提示开启权限", RouteConsts.HOME_ROUTER_INTENT_SETTING_ACTIVITY),
    ItemData("日历提醒", RouteConsts.HOME_ROUTER_CALENDAR_REMIND_ACTIVITY),
    ItemData("textSpan", RouteConsts.HOME_ROUTER_REPLACEMENT_SPAN_ACTIVITY),
    ItemData("Mavericks", RouteConsts.HOME_ROUTER_MAVERICKS_ACTIVITY),
    ItemData("Epoxy", RouteConsts.HOME_ROUTER_EPOXY_ACTIVITY),
    ItemData("CommonDemoActivity", RouteConsts.HOME_ROUTER_DEMO_ACTIVITY),
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