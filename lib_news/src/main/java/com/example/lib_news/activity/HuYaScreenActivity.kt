package com.example.lib_news.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_news.databinding.NewsHuyaScreenActivityBinding
import com.example.lib_news.databinding.NewsRvItemScreenBinding
import com.therouter.router.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

@Route(path = RouteConsts.NEWS_ROUTE_HU_YA_SCREEN_ACTIVITY)
class HuYaScreenActivity : BaseActivity<NewsHuyaScreenActivityBinding>() {
    private lateinit var mAdapter: ScreenAdapter

    override fun initView() {
        mBinding.rvScreen.adapter = ScreenAdapter().also { mAdapter = it }
        mBinding.rvScreen.setOnUnReadUpdateListener {
            mBinding.tvUnRead.text = "未读：$it"
            mBinding.tvUnRead.isGone = it == 0
        }
    }

    override fun initDate() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                TestUtils.sScreenNews.collect {
                    mAdapter.add(it)
                    mBinding.rvScreen.scrollToPosition(mAdapter.items.size - 1)
                }
            }
        }
        TestUtils.startReceiveNews()
    }

    override fun onDestroy() {
        TestUtils.endReceiveNews()
        super.onDestroy()
    }
}


class ScreenAdapter : BaseQuickAdapter<String, ScreenAdapter.VH>() {
    class VH(val viewBinding: NewsRvItemScreenBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: String?) {
        holder.viewBinding.tvScreen.text = "$position: $item"
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int) = VH(
        NewsRvItemScreenBinding.inflate(LayoutInflater.from(context), parent, false)
    )
}


object TestUtils {
    private var mTestJob: Job? = null
    val sScreenNews = MutableSharedFlow<String>()
    private val sList = mutableListOf(
        "奥术大师快到家啦可视对讲",
        "阿Q额日期额u哦亲微弱未u肉i温柔",
        "稍等马上就到",
        "啊时间打开减速电机卡扣",
        "下次女魃墓才能下班v无i奥斯卡擦拭"
    )

    fun startReceiveNews() {
        mTestJob?.cancel()
        mTestJob = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(500)
                sScreenNews.emit(getRandomScreen())
            }
        }
    }

    fun endReceiveNews() {
        mTestJob?.cancel()
    }

    private fun getRandomScreen(): String {
        return sList[Random.nextInt(0, 5)]
    }
}