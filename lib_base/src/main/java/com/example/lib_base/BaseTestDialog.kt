package com.example.lib_base

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.lib_base.databinding.BaseTestBinding

class BaseTestDialog(context: Context, val img: Bitmap?) : BaseDialog<BaseTestBinding>(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Glide.with(mBinding.iv).load(img).into(mBinding.iv)
        Glide.with(mBinding.iv).load(com.example.uilibrary.R.mipmap.img1).into(mBinding.iv)
    }

}
