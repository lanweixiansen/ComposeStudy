package com.example.libHome.mavericks

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.example.libHome.adapter.BannerAdapter
import com.example.lib_base.BaseFragment
import com.example.lib_base.ext.toast
import com.example.lib_home.databinding.HomeFragmentMavericksNetBinding
import com.example.uilibrary.uiUtils.onClick
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Mavericks使用示例，推荐在Fragment中使用。
 * 需继承 MavericksView 并重写 invalidate() 方法
 */
class MavericksNetFragment : BaseFragment<HomeFragmentMavericksNetBinding>(), MavericksView {
    // Mavericks提供的创建ViewMode的方法
    private val mViewModel: MavericksVM by fragmentViewModel()
    private val mAdapter = BannerAdapter()

    // 当ViewModel中setState方法执行完成之后，这里会收到回调
    override fun invalidate() = withState(mViewModel) { state ->

    }

    override fun initView() {
        with(mBinding) {
            smartRefresh.setOnRefreshListener {
                loadData()
            }
            rvBanner.layoutManager = LinearLayoutManager(context)
            rvBanner.adapter = mAdapter
        }
    }

    override fun initDate() {
        loadData()
    }

    override fun initListener() {
        mBinding.btnOpen.onClick {
            mViewModel.changeCheckState()
        }
    }

    private fun loadData() {
        showLoading()
        mViewModel.getBanner()
    }

    override fun initObserver() {
        super.initObserver()
        // 数据加载成功之后，这里可以收到异步回调，同invalidate()
        mViewModel.onAsync(MavericksPageState::banner,
            onSuccess = {
                //　由于Async是异步操作，当数据请求成功时，防止页面已经被销毁，数据的更新逻辑放在Lifecycle中处理
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        mAdapter.submitList(it.data)
                        // 及时cancel防止多次创建job对象
                        (this@launch).cancel()
                    }
                }
            })
        // 只有当onEach中监听的字段发生改变时，lambda表达式中的方法才会执行
        mViewModel.onEach(MavericksPageState::banner) {}
        // 设置deliveryMode为uniqueOnly可以防止在onStop时，生命周期发生变化，UI与state重新绑定时，一些弹窗或toast二次弹出的问题
        mViewModel.onEach(MavericksPageState::banner, deliveryMode = uniqueOnly("error")) {
            if (it !is Loading) {
                disLoading()
                mBinding.smartRefresh.finishRefresh()
                if (it is Success) "加载成功".toast() else "加载失败".toast()
            }
        }

        // 只针对按钮的点击事件，触发开关View的刷新，页面的其他部分不做刷新
        mViewModel.onEach(MavericksPageState::isChecked) {
            mBinding.switchView.isChecked(it)
        }
    }
}