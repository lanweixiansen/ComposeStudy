package com.example.lib_news.activity

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_news.adapter.MySongAdapter
import com.example.lib_news.adapter.MySongListBean
import com.example.lib_news.adapter.SongAdapter
import com.example.lib_news.adapter.WySongEntry
import com.example.lib_news.adapter.endAnim
import com.example.lib_news.adapter.startAnim
import com.example.lib_news.databinding.NewsActivityWxManagerBinding
import com.therouter.router.Route
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


internal var mRvTranslationY = 0

@Route(path = RouteConsts.NEWS_ROUTE_WY_MANAGER_ACTIVITY)
class WyManagerActivity : BaseActivity<NewsActivityWxManagerBinding>() {
    private val mMyAdapter = MySongAdapter()
    private val mAllAdapter = SongAdapter()
    private lateinit var helper: QuickAdapterHelper


    override fun initView() {
        helper = QuickAdapterHelper.Builder(mAllAdapter).build().addBeforeAdapter(mMyAdapter)
        with(mBinding) {
            rvAllSong.layoutManager = LinearLayoutManager(this@WyManagerActivity)
            rvAllSong.adapter = helper.adapter
            rvAllSong.setHasFixedSize(true)
        }
    }

    override fun initDate() {
        mMyAdapter.item = mMySongList[0]
        mAllAdapter.submitList(mSongList)
    }

    override fun initListener() {
        super.initListener()
        // 点击下方标签，添加到我的歌单
        mAllAdapter.setOnClickListener { bean, view ->
            view.startAnim(mMyAdapter.item?.songList?.size ?: 0, mRvTranslationY)
            mMyAdapter.addItemDate(bean.copy(isAnimItem = true, canClick = true))
        }

        mMyAdapter.setOnClickListener { bean, view ->
            var position = 0
            lifecycleScope.launch {
                mSongList.forEachIndexed { index, list ->
                    list.songList?.forEach { data ->
                        if (data.name == bean.name) {
                            position = index
                            view.endAnim(mMyAdapter.item?.songList?.size ?: 0, mRvTranslationY, data.x, data.y)
                            data.canClick = true
                        }
                    }
                }
                delay(500)
                mMyAdapter.removeItemData(bean)
                mAllAdapter.submitList(mSongList)
                mAllAdapter.notifyItemChanged(position, "sample_pay_load")
            }
        }

        mBinding.rvAllSong.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mRvTranslationY += dy
            }
        })
    }


    private val mMySongList = listOf(
        WySongEntry(
            "我的歌单",
            mutableListOf(
                MySongListBean("电子", false),
                MySongListBean("国风", false),
                MySongListBean("摇滚", false),
                MySongListBean("爱上大时代", true),
                MySongListBean("asd", true),
                MySongListBean("弃我而去", true),
                MySongListBean("奥术大", true),
                MySongListBean("地方", true),
                MySongListBean("同意", true)
            )
        )
    )

    private val mSongList = listOf(
        WySongEntry(
            "语种",
            listOf(
                MySongListBean("查询", true),
                MySongListBean("自选", true),
                MySongListBean("秩序部", true),
                MySongListBean("请问", true),
                MySongListBean("额外", true),
                MySongListBean("热天", true),
                MySongListBean("一天", true),
                MySongListBean("儿童", true),
                MySongListBean("原图", true)
            )
        ),
        WySongEntry(
            "风格",
            listOf(
                MySongListBean("欧阳", true),
                MySongListBean("乳突炎", true),
                MySongListBean("任天宇", true),
                MySongListBean("有人讨厌", true),
                MySongListBean("如同一", true),
                MySongListBean("二天", true),
                MySongListBean("古典风格", true),
                MySongListBean("冻干粉的", true),
                MySongListBean("电饭锅", true)
            )
        ),
        WySongEntry(
            "情感",
            listOf(
                MySongListBean("回归", true),
                MySongListBean("根据", true),
                MySongListBean("感觉", true),
                MySongListBean("估计", true),
                MySongListBean("电饭锅和", true),
                MySongListBean("深度覆盖", true),
                MySongListBean("构建", true),
                MySongListBean("离开", true),
                MySongListBean("那么", true)
            )
        ),
        WySongEntry(
            "主题",
            listOf(
                MySongListBean("你们", true),
                MySongListBean("匿名", true),
                MySongListBean("摇滚", true),
                MySongListBean("xvv", true),
                MySongListBean("123", true),
                MySongListBean("少的发", true),
                MySongListBean("松岛枫", true),
                MySongListBean("水电费", true),
                MySongListBean("斯蒂芬", true)
            )
        ),
        WySongEntry(
            "xx",
            listOf(
                MySongListBean("qw", true),
                MySongListBean("re", true),
                MySongListBean("tr", true),
                MySongListBean("ty", true),
                MySongListBean("iu", true),
                MySongListBean("io", true),
                MySongListBean("po", true),
                MySongListBean("cv", true),
                MySongListBean("bn", true)
            )
        ),
        WySongEntry(
            "xx",
            listOf(
                MySongListBean("qw", true),
                MySongListBean("re", true),
                MySongListBean("tr", true),
                MySongListBean("ty", true),
                MySongListBean("iu", true),
                MySongListBean("io", true),
                MySongListBean("po", true),
                MySongListBean("cv", true),
                MySongListBean("bn", true)
            )
        ),
    )
}