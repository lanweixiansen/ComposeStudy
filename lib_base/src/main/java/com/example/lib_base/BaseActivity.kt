package com.example.lib_base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.lib_base.ext.saveAs
import com.example.lib_base.ext.saveAsUnChecked
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var mBinding: VB
    private var mLoading: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass
        val vbClass: Class<VB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        setContentView(mBinding.root)
        initView()
        initDate()
        initListener()
        initObserver()
    }


    fun showLoading() {
        mLoading ?: run {
            mLoading = LoadingDialog(this)
        }
        if (mLoading?.isShowing == false)
            mLoading?.show()
    }

    fun disLoading() {
        mLoading?.dismiss()
    }


    abstract fun initView()

    abstract fun initDate()

    open fun initListener() {}

    open fun initObserver() {}
}