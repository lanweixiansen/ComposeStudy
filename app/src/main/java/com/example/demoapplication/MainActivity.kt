package com.example.demoapplication

import android.os.Bundle
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.demoapplication.databinding.ActivityMainBinding
import com.example.demoapplication.databinding.HomeActivityStubMainBinding
import com.example.demoapplication.navigation.SumFragmentNavigator
import com.example.lib_base.utils.LoadingUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(HomeActivityStubMainBinding.inflate(layoutInflater).root)
        lifecycleScope.launch {
            delay(50L)
            mBinding =
                ActivityMainBinding.bind(findViewById<ViewStub>(R.id.main_stub).inflate())
            initView()
        }

    }

    private fun initView() {
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