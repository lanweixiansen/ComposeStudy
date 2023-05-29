package com.example.lib_home.data

import androidx.lifecycle.ViewModel
import com.example.lib_base.widget.PickerView

data class ItemData(
    val content: String
)

val itemData = listOf(
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
    ItemData("Compose基本布局"),
    ItemData("Compose列表展示，图片加载"),
    ItemData("Compose & ViewModel"),
    ItemData("Intent实现转场动画"),
)

data class RegionListResp(
    var region_name: String?,
    var children: List<CityResp>?
) : PickerView.PickerItem {

    override fun getText(): String? {
        return region_name
    }
}

data class CityResp(
    var region_name: String?
) : PickerView.PickerItem {
    override fun getText(): String? {
        return region_name
    }
}