package com.example.uilibrary.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.uilibrary.R
import com.example.uilibrary.databinding.CommonTopBarLayoutBinding

class CommonTopBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {
    private val mBinding: CommonTopBarLayoutBinding
    private val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTopBar)

    init {
        mBinding = CommonTopBarLayoutBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.common_top_bar_layout, this, true)
        )
        val mTitle = typeArray.getString(R.styleable.CommonTopBar_topBarTitle)
        mBinding.tvTitle.text = mTitle
        initListener()
    }


    private fun initListener() {
        mBinding.btnBack.setOnClickListener {
            if (context is Activity) {
                (context as Activity).onBackPressed()
            }
        }
    }

    fun setTitle(content: String?): CommonTopBar {
        mBinding.tvTitle.text = content
        return this
    }
}