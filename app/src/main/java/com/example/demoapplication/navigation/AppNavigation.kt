package com.example.demoapplication.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.demoapplication.R
import com.example.libHome.HomeFragment
import com.example.lib_me.MeFragment
import com.example.lib_news.NewsFragment
import com.example.lib_square.SquareFragment

object AppNavigation {
    private lateinit var supportFragmentManager: FragmentManager


    private fun commitFragment(fragmentTag: FragmentTag) {
        val showFragment: Fragment =
            if (supportFragmentManager.findFragmentByTag(fragmentTag.tag) == null) {
                fragmentTag.fragment
            } else {
                supportFragmentManager.findFragmentByTag(fragmentTag.tag)!!
            }
        val ft = supportFragmentManager.beginTransaction()
        // 在显示一个 Fragment 之前，隐藏其他 Fragment
        for (fragment in supportFragmentManager.fragments) {
            if (fragment != showFragment) {
                ft.hide(fragment)
            }
        }
        // 显示目标 Fragment
        if (showFragment.isAdded) {
            ft.show(showFragment)
        } else {
            ft.add(R.id.fragment, showFragment, fragmentTag.tag)
        }
        ft.commit()
    }

    fun init(manager: FragmentManager) {
        supportFragmentManager = manager
        commitFragment(FragmentTag.HOME)
    }

    fun checkedFragment(id: Int) {
        commitFragment(
            when (id) {
                R.id.tab_home -> FragmentTag.HOME
                R.id.tab_square -> FragmentTag.SQUARE
                R.id.tab_news -> FragmentTag.NEWS
                R.id.tab_me -> FragmentTag.ME
                else -> FragmentTag.HOME
            }
        )
    }

    enum class FragmentTag(val tag: String, val fragment: Fragment) {
        HOME("HomeFragment", HomeFragment()),
        SQUARE("SquareFragment", SquareFragment()),
        NEWS("NewsFragment", NewsFragment()),
        ME("MeFragment", MeFragment())
    }
}