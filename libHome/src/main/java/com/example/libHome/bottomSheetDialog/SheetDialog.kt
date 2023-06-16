package com.example.libHome.bottomSheetDialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.example.libHome.data.AddressBean
import com.example.libHome.data.AreaResp
import com.example.libHome.data.CityResp
import com.example.lib_base.ext.jsonToString
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SheetDialog(context: Context) :
    BottomSheetDialog(context, com.example.lib_base.R.style.BottomSheetDialog) {
    private val mBinding: HomeBottomSheetDialogBinding

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
        behavior.peekHeight = 350
        initData()
    }

    private fun initData() {
        loadAddress()
    }


    private fun loadAddress() {
        lifecycleScope.launch {
            val bean = loadAssetsAddress()
            bean ?: return@launch
            mBinding.pickerCity.setItems(bean.provinces) { item -> setLocal(item?.city) }
            mBinding.pickerCity.notifyDataSetChanged()
            setLocal(bean.provinces[0].city)
            setArea(bean.provinces[0].city[0].area)
        }
    }

    private suspend fun loadAssetsAddress(): AddressBean? {
       return withContext(Dispatchers.IO) {
            val addressJson = jsonToString(context, "address.json")
            Gson().fromJson(addressJson, AddressBean::class.java)
        }
    }

    private fun setLocal(children: List<CityResp>?) {
        mBinding.pickerLocal.setItems(children) { setArea(it.area) }
        mBinding.pickerLocal.notifyDataSetChanged()
        mBinding.pickerLocal.selectedItemPosition = 0
    }

    private fun setArea(area: List<String>) {
        val areaList = area.map {
            AreaResp(it)
        }
        mBinding.pickerArea.setItems(areaList) {}
        mBinding.pickerArea.notifyDataSetChanged()
        mBinding.pickerArea.selectedItemPosition = 0
    }

}