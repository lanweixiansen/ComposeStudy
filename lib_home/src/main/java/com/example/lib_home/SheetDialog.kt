package com.example.lib_home

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.lib_home.databinding.HomeBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class SheetDialog(context: Context) : BottomSheetDialog(context, R.style.BottomSheetDialog) {
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
    }
}