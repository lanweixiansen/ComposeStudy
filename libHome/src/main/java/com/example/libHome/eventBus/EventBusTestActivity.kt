package com.example.libHome.eventBus

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts.HOME_EVENT_BUS_ACTIVITY
import com.example.lib_home.databinding.HomeActivityEventBusTestBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@Route(path = HOME_EVENT_BUS_ACTIVITY)
class EventBusTestActivity : BaseActivity<HomeActivityEventBusTestBinding>() {
    private var mFragment: EventBusTestView? = null

    override fun initView() {

    }

    override fun initDate() {
    }

    override fun initListener() {
        super.initListener()
        mBinding.btnSendEventBus.setOnClickListener {
            EventBus.getDefault().post(EventBusTestBean("这是一条EventBus指令"))
        }
        mBinding.btnRemoveFragment.setOnClickListener {
            mBinding.tvEventTip.text = ""
            mFragment?.let { it1 -> supportFragmentManager.beginTransaction().remove(it1).commit() }
        }
    }


    @Subscribe(priority = 2)
    fun eventTest(event: EventBusTestBean) {
        mFragment ?: run {
            mFragment = EventBusTestView()
        }
        if (mFragment?.isAdded == true) {
            return
        }
        mBinding.tvEventTip.text = """
            这是优先级为2的EventBus......
            Activity收到了EventBus......
            开始加载Fragment到LinearLayout中......
            发送一个新的EventBus将数据传输到LinearLayout中......
        """.trimIndent()
        supportFragmentManager.beginTransaction().add(mBinding.linear.id, mFragment!!)
            .commit()
        mBinding.tvEventTip.post {
            EventBus.getDefault().post(EventBusTestBean2(event.content))
        }
    }

    @Subscribe(priority = 1)
    fun eventTest2(event: EventBusTestBean) {
        mBinding.tvEventTip2.text = """
            这是优先级为1的EventBus......
        """.trimIndent()
    }

    override fun useEventBus(): Boolean {
        return true
    }
}