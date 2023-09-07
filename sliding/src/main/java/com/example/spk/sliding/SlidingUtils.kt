package com.example.spk.sliding

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ContentFrameLayout
import androidx.fragment.app.Fragment

object SlidingUtils {
    private fun createdView(context: Context, view: View): SlidingSuspensionView {
        val slidingView = SlidingSuspensionView(context).apply {
            addView(view)
        }
        slidingView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return slidingView
    }


    /**
     * 添加Activity悬浮窗
     */
    fun showSliding(activity: Activity, view: View) {
        activity.findViewById<ContentFrameLayout>(android.R.id.content)
            .addView(createdView(activity, view))
    }

    /**
     * 添加Fragment悬浮窗
     */
    fun showSliding(fragment: Fragment, view: View) {
        if (fragment.view is ViewGroup) {
            (fragment.view as ViewGroup).addView(createdView(fragment.requireContext(), view))
        }
    }

    /**
     * 添加View悬浮窗
     */
    fun showSliding(view: View, showView: View) {
        if (view is ViewGroup) {
            view.addView(createdView(view.context, showView))
        }
    }

    /**
     * 添加APP悬浮窗
     */
    fun showApplicationSliding(activity: Activity, applicationContext: Application, view: View) {
        applicationContext.registerActivityLifecycleCallbacks(SlidingLifecycleCallback().apply {
            attach(activity, view)
        })
    }
}