package com.example.libHome.bottomSheetDialog

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.example.lib_base.utils.dp2px
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeDialiogBottomShareBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BottomShareDialog(context: Context) :
    BottomSheetDialog(context, com.example.lib_base.R.style.BottomSheetDialog) {
    private val mBinding: HomeDialiogBottomShareBinding
    private var mAnimList = listOf<ObjectAnimator>()

    init {
        mBinding = HomeDialiogBottomShareBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.home_dialiog_bottom_share, null, false)
        )
        setContentView(mBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAnim(mBinding.anim1, mBinding.anim2, mBinding.anim3, mBinding.anim4)
    }

    override fun onStart() {
        super.onStart()
        mBinding.btnCancel.setOnClickListener { dismiss() }
    }

    private fun startAnim(vararg view: ImageView) {
        mAnimList = view.map {
            val anim1 = PropertyValuesHolder.ofFloat("translationY", dp2px(100f), dp2px(0f))
            val anim2 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
            ObjectAnimator.ofPropertyValuesHolder(it, anim1, anim2).apply {
                duration = 500
                interpolator = DecelerateInterpolator()
            }
        }
        lifecycleScope.launch {
            mAnimList.forEach {
                it.start()
                delay(5)
            }
        }
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        mAnimList.forEach { it.cancel() }
        super.setOnDismissListener(listener)
    }
}