package com.example.demoapplication.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isGone
import com.example.demoapplication.R
import com.example.demoapplication.databinding.BottomItemViewBinding
import com.example.demoapplication.databinding.ViewBottomNavigationLayoutBinding
import com.example.lib_base.ext.toGone
import com.example.lib_base.ext.toVisible
import com.example.lib_base.utils.loadImage

class BottomNavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {
    private val mBinding: ViewBottomNavigationLayoutBinding
    private var mOnTabChecked: ((Int) -> Unit)? = null
    private val mTabData = listOf(
        TabData(R.string.tab_home, R.drawable.home),
        TabData(R.string.tab_square, R.drawable.square),
        TabData(R.string.tab_news, R.drawable.news),
        TabData(R.string.tab_me, R.drawable.me),
    )

    init {
        mBinding =
            ViewBottomNavigationLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        initView()
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
            bottomItemView.setIcon(mTabData[index].tabIcon)
            bottomItemView.setTab(mTabData[index].tabName)
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

}

class BottomItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {

    private val mBinding: BottomItemViewBinding

    init {
        mBinding = BottomItemViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTab(@StringRes title: Int) {
        mBinding.tvTab.text = context.getString(title)
    }

    fun setIcon(@DrawableRes icon: Int) {
        mBinding.ivTab.loadImage(icon)
    }

    fun checked() {
        mBinding.tvTab.toVisible()
    }

    fun resect() {
        mBinding.tvTab.toGone()
    }

    fun canChange() = mBinding.tvTab.isGone
}

data class TabData(
    @StringRes
    val tabName: Int,
    @DrawableRes
    val tabIcon: Int
)