package com.example.libHome.viewStub

import android.os.Bundle
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lib_base.ext.toGone
import com.example.lib_base.ext.toVisible
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeActivityViewStubTest2Binding
import com.example.lib_home.databinding.HomeViewStubHeadersBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewStubTest2Activity : AppCompatActivity() {
    private lateinit var mBinding: HomeViewStubHeadersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(HomeActivityViewStubTest2Binding.inflate(layoutInflater).root)
        lifecycleScope.launch {
            delay(100L)
            mBinding =
                HomeViewStubHeadersBinding.bind(findViewById<ViewStub>(R.id.view_stub).inflate())
            initClick()
        }
    }

    private fun initClick() {
        with(mBinding) {
            btn2Hide.setOnClickListener { mBinding.homeImageview.toGone() }
            btn2Show.setOnClickListener { mBinding.homeImageview.toVisible() }
            btn3Hide.setOnClickListener { mBinding.homeImageview3.toGone() }
            btn3Show.setOnClickListener { mBinding.homeImageview3.toVisible() }
        }
    }

}