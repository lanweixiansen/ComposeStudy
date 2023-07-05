package com.example.lib_base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.lib_base.ext.saveAs
import com.example.lib_base.ext.saveAsUnChecked
import com.example.uilibrary.widget.LoadingDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    lateinit var mBinding: VB
    private var mLoading: LoadingDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        val vbClass: Class<VB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (needDelay()) {
            lifecycleScope.launch {
                delay(150)
                initView()
                initDate()
                initListener()
                initObserver()
            }
        } else {
            initView()
            initDate()
            initListener()
            initObserver()
        }
    }

    override fun onStart() {
        super.onStart()
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        mLoading?.dismiss()
    }

    fun showLoading() {
        mLoading ?: run {
            mLoading = context?.let { LoadingDialog(it) }
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

    open fun useEventBus() = false

    open fun needDelay() = false
}