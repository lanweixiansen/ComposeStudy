package com.example.lib_home

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.lib_base.widget.PickerView
import com.example.lib_home.data.CityResp
import com.example.lib_home.data.RegionListResp
import com.example.lib_home.data.itemData
import com.example.lib_home.databinding.HomeBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class SheetDialog(context: Context) : BottomSheetDialog(context, R.style.BottomSheetDialog) {
    private val mBinding: HomeBottomSheetDialogBinding

    private val cityList2 =
        listOf(CityResp("北京1"), CityResp("北京2"), CityResp("北京3"), CityResp("北京4"))
    private val cityList = listOf(
        RegionListResp("北京1", cityList2),
        RegionListResp("北京2", cityList2),
        RegionListResp("北京3", cityList2)
    )

    init {
        mBinding = HomeBottomSheetDialogBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.home_bottom_sheet_dialog, null, false)
        )
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        val behavior =
            BottomSheetBehavior.from(findViewById<LinearLayout>(com.google.android.material.R.id.design_bottom_sheet)!!)
//        behavior.peekHeight = 350
        initData()
    }

    private fun initData() {
        mBinding.pickerCity.setAdapter(object : PickerView.Adapter<PickerView.PickerItem>() {
            override fun getItemCount(): Int = 0

            override fun getItem(index: Int): PickerView.PickerItem? = null

        })

        mBinding.pickerLocal.setAdapter(object : PickerView.Adapter<PickerView.PickerItem>() {
            override fun getItemCount(): Int = 0

            override fun getItem(index: Int): PickerView.PickerItem? = null

        })

        mBinding.pickerCity.setItems(
            cityList
        ) { item -> mBinding.pickerLocal.setItems(item?.children, null) }
        mBinding.pickerLocal.setItems(cityList[0].children, null)
    }
}