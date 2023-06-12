package com.example.libHome.data

import com.example.uilibrary.widget.PickerView

data class AddressBean(
    val provinces: List<RegionListResp>
)

data class RegionListResp(
    val name: String,
    val city: List<CityResp>
) : PickerView.PickerItem {
    override fun getText(): String {
        return name
    }
}

data class CityResp(
    val name: String,
    val area: List<String>
) : PickerView.PickerItem {
    override fun getText(): String {
        return name
    }
}

data class AreaResp(
    val name: String
): PickerView.PickerItem {
    override fun getText(): String {
        return name
    }
}