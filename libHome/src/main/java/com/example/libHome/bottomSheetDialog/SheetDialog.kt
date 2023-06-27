package com.example.libHome.bottomSheetDialog

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ScreenUtils
import com.example.libHome.data.AddressBean
import com.example.libHome.data.AreaResp
import com.example.libHome.data.CityResp
import com.example.lib_base.ext.jsonToString
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeBottomSheetDialogBinding
import com.example.libnet.manager.HttpManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.moshi.JsonAdapter
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
        behavior.peekHeight = 550
        behavior.maxHeight = ScreenUtils.getAppScreenHeight()
        initData()
        mBinding.homeExpend.setOnClickListener {
            behavior.state = STATE_EXPANDED
        }
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
            val jsonAdapter: JsonAdapter<AddressBean> =
                HttpManager.moshi.adapter(AddressBean::class.java)
            jsonAdapter.fromJson(addressJson)
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


    override fun onStop() {
        super.onStop()
    }
}
