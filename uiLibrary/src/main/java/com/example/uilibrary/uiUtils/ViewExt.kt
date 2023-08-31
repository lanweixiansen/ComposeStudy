package com.example.uilibrary.uiUtils

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.launch
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

/**
 * View 添加底部导航栏间距
 */
fun View.addMarginToNavigationBar() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemWindowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(0, 0, 0, systemWindowInsets.bottom)
        insets
    }
}

/**
 * View 添加顶部状态栏间距
 */
fun View.addMarginToEqualStatusBar() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val systemWindowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(0, systemWindowInsets.top, 0, 0)
        insets
    }
}

inline fun <reified T : ViewBinding> ViewGroup.viewBinding() =
    ViewBindingDelegate(T::class.java, this)

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    FragmentViewBindingDelegate(T::class.java, this)

inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding() =
    ActivityBindingDelegate(T::class.java)


class ViewBindingDelegate<T : ViewBinding>(
    private val bindingClass: Class<T>,
    val fragment: ViewGroup
) : ReadOnlyProperty<ViewGroup, T> {
    private var binding: T? = null

    override fun getValue(thisRef: ViewGroup, property: KProperty<*>): T {
        binding?.let { return it }

        val inflateMethod =
            bindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java)
        @Suppress("UNCHECKED_CAST")
        binding = inflateMethod.invoke(null, LayoutInflater.from(thisRef.context), thisRef) as T
        return binding!!
    }
}

class ActivityBindingDelegate<T : ViewBinding>(
    private val bindingClass: Class<T>,
) : ReadOnlyProperty<AppCompatActivity, T> {
    private var binding: T? = null

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        binding?.let { return it }

        val inflateMethod =
            bindingClass.getMethod(
                "inflate",
                LayoutInflater::class.java,
                AppCompatActivity::class.java
            )
        binding = inflateMethod.invoke(null, LayoutInflater.from(thisRef), thisRef) as T
        return binding!!
    }
}


class FragmentViewBindingDelegate<T : ViewBinding>(
    bindingClass: Class<T>,
    private val fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {
    private val clearBindingHandler by lazy(LazyThreadSafetyMode.NONE) { Handler(Looper.getMainLooper()) }
    private var binding: T? = null

    private val bindMethod = bindingClass.getMethod("bind", View::class.java)

    init {
        fragment.lifecycleScope.launch {
            fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        clearBindingHandler.post { binding = null }
                    }
                })
            }
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding != null && binding?.root !== thisRef.view) {
            binding = null
        }
        binding?.let { return it }
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }
        @Suppress("UNCHECKED_CAST")
        binding = bindMethod.invoke(null, thisRef.requireView()) as T
        return binding!!
    }
}
