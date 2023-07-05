package com.example.lib_me

import com.example.lib_base.BaseFragment
import com.example.lib_me.databinding.MeFragmentMineBinding
import io.flutter.embedding.android.FlutterFragment

class MeFragment : BaseFragment<MeFragmentMineBinding>() {
    override fun initView() {
        childFragmentManager.beginTransaction().add(
            R.id.flutter_fragment, FlutterFragment.withCachedEngine("my_engine_id")
                .shouldAttachEngineToActivity(false).build(), "FlutterFragment"
        ).commit()
    }

    override fun initDate() {

    }

    override fun onResume() {
        childFragmentManager.findFragmentByTag("FlutterFragment")?.let {
            (it as? FlutterFragment)?.onResume()
        }
        super.onResume()
    }

    override fun onPause() {
        childFragmentManager.beginTransaction().remove(FlutterFragment.withCachedEngine("my_engine_id")
            .shouldAttachEngineToActivity(false).build()).commit()
        super.onPause()
    }

    override fun onStop() {
        childFragmentManager.findFragmentByTag("FlutterFragment")?.let {
            (it as? FlutterFragment)?.onStop()
        }
        super.onStop()
    }

    override fun onDestroyView() {
        childFragmentManager.beginTransaction().remove(FlutterFragment.withCachedEngine("my_engine_id")
            .shouldAttachEngineToActivity(false).build()).commit()
        super.onDestroyView()
    }

}