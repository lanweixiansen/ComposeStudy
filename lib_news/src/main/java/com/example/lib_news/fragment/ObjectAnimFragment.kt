package com.example.lib_news.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.animation.BounceInterpolator
import android.view.animation.CycleInterpolator
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts.NEWS_ROUTE_OBJECT_ANIM_FRAGMENT
import com.example.lib_news.databinding.NewsFragmentObjectAnimBinding
import com.example.lib_news.widget.RotationView
import com.example.uilibrary.widget.RoundImageView
import com.therouter.router.Route


@Route(path = NEWS_ROUTE_OBJECT_ANIM_FRAGMENT)
class ObjectAnimFragment : BaseActivity<NewsFragmentObjectAnimBinding>() {
    override fun initView() {}

    override fun initDate() {}

    override fun initListener() {
        super.initListener()
        mBinding.an1.setOnClickListener {
            showAnim1(mBinding.an1)
        }
        mBinding.an2.setOnClickListener {
            showAnim2(mBinding.an2)
        }
        mBinding.an3.setOnClickListener {
            showAnim3(mBinding.an3)
        }
    }

    // 无限旋转
    private fun showAnim3(imageView: RotationView) {
        lifecycle.addObserver(imageView)
        imageView.startRotation()
    }

    // view抖动效果实现
    private fun showAnim2(an2: RoundImageView) {
        // 补间动画实现
//        val anim = RotateAnimation(
//            -3f,
//            3f,
//            Animation.RELATIVE_TO_SELF,
//            0.5f,
//            Animation.RELATIVE_TO_SELF,
//            0f
//        )
//        anim.duration = 500
//        anim.interpolator = CycleInterpolator(3f)
//        an2.startAnimation(anim)

        // 属性动画实现
        an2.pivotX = (an2.width / 2f)
        an2.pivotY = an2.height / 2f
        val objectAnimator = ObjectAnimator.ofFloat(an2, "rotation", -3f, 3f)
        objectAnimator.duration = 500
        objectAnimator.interpolator = CycleInterpolator(3f)
        objectAnimator.start()
        objectAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                an2.rotation = 0f
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    // 放大缩小加回弹效果
    private fun showAnim1(an1: RoundImageView) {
        val holder1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.1f)
        val holder2 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.1f)
        val holder3 = PropertyValuesHolder.ofFloat("scaleX", 1.0f)
        val holder4 = PropertyValuesHolder.ofFloat("scaleY", 1.0f)
        val anim1 = ObjectAnimator.ofPropertyValuesHolder(an1, holder1, holder2).setDuration(200)
        val anim2 = ObjectAnimator.ofPropertyValuesHolder(an1, holder3, holder4).apply {
            duration = 500
            interpolator = BounceInterpolator()
        }
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(anim1, anim2)
        animatorSet.start()
    }
}