package com.example.demoapplication.widget

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.demoapplication.R
import com.example.demoapplication.databinding.BottomItemViewBinding
import com.example.demoapplication.databinding.ViewBottomNavigationLayoutBinding
import com.example.lib_base.utils.loadImage
import com.example.uilibrary.uiUtils.dp2px
import com.example.uilibrary.uiUtils.toGone
import com.example.uilibrary.uiUtils.toVisible

class BottomNavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {
    private val mBinding: ViewBottomNavigationLayoutBinding
    private var mOnTabChecked: ((Int) -> Unit)? = null
    private val mTabData = listOf(
        TabData(R.string.tab_home, R.drawable.home),
        TabData(R.string.tab_square, R.drawable.square),
        TabData(R.string.tab_news, R.drawable.news),
        TabData(R.string.tab_me, R.drawable.me, true)
    )

    init {
        mBinding =
            ViewBottomNavigationLayoutBinding.inflate(LayoutInflater.from(context), this)
        initView()
//        setPadding(0, dp2px(4), 0, dp2px(4))
        setBackgroundResource(R.drawable.bottom_tab_bg)
    }

    private fun initView() {
        with(mBinding) {
            initTab(tabHome, tabSquare, tabNews, tabMe)
            checkSelect(tabHome)
            onClick(tabHome, tabSquare, tabNews, tabMe)
        }
    }

    private fun onClick(vararg bottomItemView: BottomItemView) {
        bottomItemView.forEach { tab ->
            tab.setOnClickListener {
                checkSelect(tab)
            }
        }
    }

    private fun initTab(vararg tab: BottomItemView) {
        tab.forEachIndexed { index, bottomItemView ->
            bottomItemView.setInfo(mTabData[index])
        }
    }

    private fun checkSelect(tab: BottomItemView) {
        if (tab.canChange()) {
            restBottomView()
            tab.checked()
            mOnTabChecked?.invoke(tab.id)
        }
    }

    private fun restBottomView() {
        mBinding.tabSquare.resect()
        mBinding.tabHome.resect()
        mBinding.tabMe.resect()
        mBinding.tabNews.resect()
    }

    fun setTabClickListener(onTabChecked: (id: Int) -> Unit) {
        this.mOnTabChecked = onTabChecked
    }

    fun isMeChecked() = mBinding.tabMe.isChecked()

    fun checkMe() = checkSelect(mBinding.tabMe)

    fun checkHome() = checkSelect(mBinding.tabHome)
}

class BottomItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {
    private val mBinding: BottomItemViewBinding
    private var mCanRefresh = false

    init {
        mBinding = BottomItemViewBinding.inflate(LayoutInflater.from(context), this)
        orientation = VERTICAL
        gravity = Gravity.CENTER
        layoutTransition = LayoutTransition()
    }

    fun checked() {
        mBinding.tvTab.toVisible()
    }

    fun resect() {
        mBinding.tvTab.toGone()
    }

    fun canChange() = mBinding.tvTab.isGone || mCanRefresh
    fun isChecked() = mBinding.tvTab.isVisible

    fun setInfo(tabData: TabData) {
        mBinding.tvTab.text = context.getString(tabData.tabName)
        mBinding.ivTab.loadImage(tabData.tabIcon)
        mCanRefresh = tabData.canRefresh ?: false
    }
}

data class TabData(
    @StringRes
    val tabName: Int,
    @DrawableRes
    val tabIcon: Int,
    val canRefresh: Boolean? = false
)