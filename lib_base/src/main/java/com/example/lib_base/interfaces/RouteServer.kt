package com.example.lib_base.interfaces

import com.alibaba.android.arouter.facade.template.IProvider

interface RouteServer : IProvider {

    fun getLibName(): String
}