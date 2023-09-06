package com.example.libHome.sliding

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ContentFrameLayout
import androidx.fragment.app.Fragment
import com.example.lib_base.manager.AppManager
import com.example.uilibrary.widget.SlidingSuspensionView

object SlidingUtils {

    fun showSliding(activity: Activity, view: View) {
        val slidingView = SlidingSuspensionView(activity).apply {
            addView(view)
        }
        activity.findViewById<ContentFrameLayout>(android.R.id.content).addView(slidingView)
    }

    fun showSliding(fragment: Fragment, view: View) {
        val slidingView = SlidingSuspensionView(fragment.requireContext()).apply {
            addView(view)
        }
        if (fragment.view is ViewGroup) {
            (fragment.view as ViewGroup).addView(slidingView)
        }
    }

    fun showSliding(view: View, showView: View) {
        val slidingView = SlidingSuspensionView(view.context).apply {
            addView(showView)
        }
        if (view is ViewGroup) {
            view.addView(slidingView)
        }
    }

    fun showSliding(activity: Activity) {
        AppManager.getApplicationContext()
            .registerActivityLifecycleCallbacks(SlidingLifecycleCallback().apply {
                attach(activity)
            })
    }
}