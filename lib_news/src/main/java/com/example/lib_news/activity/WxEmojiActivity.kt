package com.example.lib_news.activity

import android.animation.Animator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_news.adapter.WxAdapter
import com.example.lib_news.data.WxEntity
import com.example.lib_news.databinding.NewsWxEmojiActivityBinding
import com.example.uilibrary.uiUtils.toGone
import com.example.uilibrary.uiUtils.toVisible
import com.therouter.router.Route

@Route(path = RouteConsts.NEWS_ROUTE_WX_EMOJI_ACTIVITY)
class WxEmojiActivity : BaseActivity<NewsWxEmojiActivityBinding>() {
    private val mWxAdapter = WxAdapter()
    override fun initView() {
        with(mBinding.rvChat) {
            layoutManager = LinearLayoutManager(context)
            adapter = mWxAdapter
        }
        lifecycle.addObserver(mBinding.animShit)
    }

    override fun initDate() {}

    override fun initListener() {
        mBinding.btnAddEmoji.setOnClickListener {
            mWxAdapter.add(WxEntity(0))
        }
        mBinding.btnAddBao.setOnClickListener {
            mWxAdapter.add(WxEntity(1, true))
        }
        mWxAdapter.setOnAddDataListener {
            mBinding.rvChat.scrollToPosition(mWxAdapter.itemCount - 1)
        }
        mWxAdapter.setOnAnimEndListener {
            mBinding.lottieFire.toVisible()
            mBinding.lottieFire.speed = 1.5f
            mBinding.lottieFire.playAnimation()
            mBinding.lottieFire.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    mBinding.lottieFire.removeAllAnimatorListeners()
                    mBinding.lottieFire.toGone()
                    mBinding.animShit.beginAnim(6)
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
    }

    override fun onDestroy() {
        mBinding.lottieFire.cancelAnimation()
        mBinding.lottieFire.removeAllAnimatorListeners()
        super.onDestroy()
    }
}