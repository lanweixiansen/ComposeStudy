package com.example.uilibrary.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uilibrary.databinding.UiSwitchViewBinding
import com.example.uilibrary.uiUtils.onClick

class SwitchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val mBinding: UiSwitchViewBinding
    private var mOnChecked: ((Boolean) -> Unit)? = null

    init {
        mBinding = UiSwitchViewBinding.inflate(LayoutInflater.from(context), this, true)
        initListener()
    }

    private fun initListener() {
        mBinding.switchView.setMaxProgress(0.45f)
        mBinding.switchView.speed = 2f
        mBinding.switchView.onClick {
            this.isSelected = !this.isSelected
            if (this.isSelected) {
                mBinding.switchView.playAnimation()
            } else {
                mBinding.switchView.progress = 0f
            }
            mOnChecked?.invoke(this.isSelected)
        }
    }

    fun setOnSwitchCheckedStateChangeListener(onChecked: (isChecked: Boolean) -> Unit) {
        mOnChecked = onChecked
    }

    fun isChecked() = this.isSelected
}