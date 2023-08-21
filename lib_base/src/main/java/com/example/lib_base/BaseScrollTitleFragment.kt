package com.example.lib_base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lib_base.databinding.BaseFragmentScrollTitleBinding
import com.example.lib_base.ext.saveAs

abstract class BaseScrollTitleFragment : Fragment() {
    private lateinit var mBinding: BaseFragmentScrollTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = BaseFragmentScrollTitleBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.content.addView(setContentView())
        mBinding.topBar.title = setTitle()
        mBinding.collapsingBar.setCollapsedTitleTextAppearance(R.style.CollapsedTitle)
        mBinding.collapsingBar.setExpandedTitleTextAppearance(R.style.ExpandedTitle)
        mBinding.backButton.setOnClickListener { context?.saveAs<Activity>()?.finish() }
    }

    abstract fun setContentView(): View

    abstract fun setTitle(): String
}