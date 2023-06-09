package com.example.libHome.linearLayout

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentActivity
import com.example.lib_base.ext.toast

class TestLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {
    private var mFragment: TestFragment? = null


    /**
     * 添加活动Fragment
     */
    fun addFragmentView() {
        if (mFragment != null) return
        mFragment = TestFragment()
        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .add(this.id, mFragment!!).commit()
        mFragment?.setSportData(1000)
    }


    /**
     * 检查当前View数量
     */
    fun checkViewCount() {
        "${this.childCount}".toast(context)
    }

    /**
     * 隐藏活动Fragment
     */
    fun hideFragmentView() {
        this.removeView(mFragment?.view)
    }

    /**
     * 重新显示活动Fragment
     */
    fun reAddFragmentView() {
        if (this.indexOfChild(mFragment?.view) == -1) {
            addView(mFragment?.view)
        }
    }

    /**
     * 移除活动Fragment
     */
    fun removeFragmentView() {
        mFragment?.let {
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
                .remove(it).commit()
        }
        mFragment = null
    }
}