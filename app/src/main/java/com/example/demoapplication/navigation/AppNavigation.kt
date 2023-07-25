package com.example.demoapplication.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.demoapplication.R
import com.example.demoapplication.appTask.ApplicationTask
import com.example.libHome.HomeFragment
import com.example.lib_base.manager.AppData
import com.example.lib_base.manager.AppManager
import com.example.lib_news.NewsFragment
import com.example.lib_square.SquareFragment
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.RenderMode

/**
 * APP导航
 */
object AppNavigation {
    private var mSupportFragmentManager: FragmentManager? = null

    private fun commitFragment(fragmentTag: FragmentTag) {
        if (fragmentTag == FragmentTag.ME && !AppData.isLogin()) {
            AppManager.goLogin()
            return
        }
        mSupportFragmentManager?.let { supportManager ->
            val showFragment: Fragment =
                if (supportManager.findFragmentByTag(fragmentTag.tag) == null) {
                    createdFragment(fragmentTag)
                } else {
                    supportManager.findFragmentByTag(fragmentTag.tag)!!
                }
            val ft = supportManager.beginTransaction()
            // 在显示一个 Fragment 之前，隐藏其他 Fragment
            for (fragment in supportManager.fragments) {
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
    }

    fun init(manager: FragmentManager) {
        mSupportFragmentManager = manager
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

    private fun createdFragment(tag: FragmentTag): Fragment {
        return when (tag) {
            FragmentTag.HOME -> HomeFragment()
            FragmentTag.SQUARE -> SquareFragment()
            FragmentTag.NEWS -> NewsFragment()
            FragmentTag.ME -> createdFlutterFragment()
        }
    }

    private fun createdFlutterFragment(): FlutterFragment {
        var fragment: FlutterFragment? = null
        kotlin.runCatching {
            FlutterFragment.withCachedEngine("my_engine_id").renderMode(
                RenderMode.texture
            ).build() as FlutterFragment
        }.onFailure {
            ApplicationTask.initFlutterEngin(AppManager.getApplicationContext())
            fragment = FlutterFragment.withNewEngine().renderMode(RenderMode.texture).build()
        }.onSuccess {
            fragment = it
        }
        return fragment!!
    }

    fun finishInit() {
        mSupportFragmentManager = null
    }

    enum class FragmentTag(val tag: String) {
        HOME("HomeFragment"), SQUARE("SquareFragment"), NEWS("NewsFragment"), ME("MeFragment")
    }
}