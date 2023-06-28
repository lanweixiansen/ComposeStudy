package com.example.lib_base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.example.lib_base.ext.isEqualType
import com.example.lib_base.ext.saveAs
import com.example.lib_base.ext.saveAsUnChecked
import java.lang.reflect.ParameterizedType

abstract class BaseDialog<VB : ViewBinding>(context: Context) : Dialog(context),
    DefaultLifecycleObserver {
    lateinit var mBinding: VB

    val lifecycle by lazy {
        if (context.isEqualType<LifecycleOwner>())
            context.saveAs<LifecycleOwner>().lifecycle else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val type = javaClass.genericSuperclass
        val vbClass: Class<VB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        super<Dialog>.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        lifecycle?.addObserver(this)
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        lifecycle?.removeObserver(this)
    }
}