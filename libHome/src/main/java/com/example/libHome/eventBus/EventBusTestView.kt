package com.example.libHome.eventBus

import com.example.lib_base.BaseFragment
import com.example.lib_home.databinding.HomeFragmentEventBusTestBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusTestView: BaseFragment<HomeFragmentEventBusTestBinding>() {
    override fun initView() {

    }

    override fun initDate() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBus(event: EventBusTestBean2) {
        mBinding.tvEventBus.text = event.content
    }

    override fun useEventBus(): Boolean {
        return true
    }
}