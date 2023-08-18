package com.example.uilibrary.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.uilibrary.R
import com.example.uilibrary.databinding.CommonTopBarLayoutBinding

class CommonTopBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {
    private val mBinding: CommonTopBarLayoutBinding
    private val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTopBar)

    init {
        orientation = VERTICAL
        setBackgroundColor(Color.WHITE)
        mBinding = CommonTopBarLayoutBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.common_top_bar_layout, this, true)
        )
        val mTitle = typeArray.getString(R.styleable.CommonTopBar_topBarTitle)
        val showBackIcon = typeArray.getBoolean(R.styleable.CommonTopBar_show_back_icon, true)
        mBinding.btnBack.isInvisible = !showBackIcon
        mBinding.tvTitle.text = mTitle
        initListener()
        typeArray.recycle()
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