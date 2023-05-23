package com.example.demoapplication.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.demoapplication.navigation.WindowFrameLayout

/**
 * 处理Navigation重建问题
 */
class WindowNavHostFragment : NavHostFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val frameLayout = WindowFrameLayout(inflater.context)
        frameLayout.id = id
        return frameLayout
    }
}