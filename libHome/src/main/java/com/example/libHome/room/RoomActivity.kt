package com.example.libHome.room

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.therouter.router.Route
import com.example.libHome.adapter.BannerDBAdapter
import com.example.libHome.net.viewModel.HomeViewModel
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeActivityRoomBinding

@Route(path = RouteConsts.HOME_ROUTER_ROOM_ACTIVITY)
class RoomActivity : BaseActivity<HomeActivityRoomBinding>() {
    private val mViewModel by viewModels<HomeViewModel>()
    private val mAdapter = BannerDBAdapter()

    override fun initView() {
        mBinding.rvBanner.layoutManager = LinearLayoutManager(this)
        mBinding.rvBanner.adapter = mAdapter
    }

    override fun initDate() {
        mViewModel.getBannerByDB()
        mViewModel.mDBBanner.observe(this) {
            mAdapter.submitList(it)
        }
    }
}