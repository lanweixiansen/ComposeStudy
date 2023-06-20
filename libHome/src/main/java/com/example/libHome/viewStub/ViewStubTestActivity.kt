package com.example.libHome.viewStub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lib_base.BaseActivity
import com.example.lib_home.databinding.HomeActivityViewStubTestBinding

class ViewStubTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mBinding = HomeActivityViewStubTestBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}