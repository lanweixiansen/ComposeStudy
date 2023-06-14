package com.example.libHome.bottomSheetDialog

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
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

    private fun startAnim(vararg view: ImageView) {
        val list = view.map {
            val anim1 = PropertyValuesHolder.ofFloat("translationY", dp2px(300f), dp2px(0f))
            val anim2 = PropertyValuesHolder.ofFloat("alpha", 0f, 0.2f, 1f)
            ObjectAnimator.ofPropertyValuesHolder(it, anim1, anim2).apply {
                duration = 500
                interpolator = DecelerateInterpolator()
            }
        }
        lifecycleScope.launch {
            list.forEach {
                it.start()
                delay(5)
            }
        }
    }

}