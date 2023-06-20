package com.example.demoapplication

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.demoapplication.databinding.ActivityMainBinding
import com.example.demoapplication.navigation.SumFragmentNavigator
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.LoadingUtils

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var navController: NavController

    override fun initView() {
        val navView = mBinding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val fragmentNavigator =
            SumFragmentNavigator(this, navHostFragment.childFragmentManager, navHostFragment.id)
        navController.navigatorProvider.addNavigator(fragmentNavigator)
        navController.setGraph(R.navigation.navi_host)
        navView.setupWithNavController(navController)
    }

    override fun initDate() {}

    override fun onBackPressed() {
        // 最小化到桌面
        moveTaskToBack(true)
        // 退出APP
//        AppExit.onBackPressed(this)
    }

    override fun onDestroy() {
        LoadingUtils.disLoading()
        super.onDestroy()
    }

}