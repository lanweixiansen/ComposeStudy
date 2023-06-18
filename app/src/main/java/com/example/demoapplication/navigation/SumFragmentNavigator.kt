package com.example.demoapplication.navigation

import android.content.Context
import android.util.Log
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

/**
 *
 * 可以重写navigate()方法{@link Destination#navigate(Destination, Bundle, NavOptions, Navigator.Extras)}
 * 将显示Fragment#replace()改成hide()和show()方法
 *
 * 需要在类头添加Navigator.Name注解，添加一个名称
 */
@Navigator.Name("sumFragment")
class SumFragmentNavigator(context: Context, manager: FragmentManager, containerId: Int) :
    FragmentNavigator(context, manager, containerId) {

    private val mContext = context
    private val mManager = manager
    private val mContainerId = containerId

    private val TAG = "SumFragmentNavigator"

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        if (mManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already saved its state"
            )
            return
        }
        for (entry in entries) {
            navigate(entry, navOptions, navigatorExtras)
        }
    }

    private fun navigate(
        entry: NavBackStackEntry, navOptions: NavOptions?, navigatorExtras: Navigator.Extras?
    ) {
        val savedIds = try {
            val targetClass: Class<*> = this.javaClass.superclass
            val obj = targetClass.cast(this) as FragmentNavigator
            val field = targetClass.getDeclaredField("savedIds")
            //修改访问限制
            field.isAccessible = true

            field[obj] as MutableSet<String>
        } catch (e: Exception) {
            e.printStackTrace()
            mutableSetOf()
        }
        val initialNavigation = state.backStack.value.isEmpty()
        val restoreState =
            (navOptions != null && !initialNavigation && navOptions.shouldRestoreState() && savedIds.remove(
                entry.id
            ))
        if (restoreState) {
            // Restore back stack does all the work to restore the entry
            mManager.restoreBackStack(entry.id)
            state.push(entry)
            return
        }
        val ft = createFragmentTransaction(entry, navOptions)

        if (!initialNavigation) {
            ft.addToBackStack(entry.id)
        }

        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }
        ft.commit()
        // The commit succeeded, update our view of the world
        state.push(entry)
    }

    private val fragmentCache = SparseArray<Fragment>()
    private fun createFragmentTransaction(
        entry: NavBackStackEntry, navOptions: NavOptions?
    ): FragmentTransaction {
        val destination = entry.destination as Destination
        val args = entry.arguments
        val className = destination.className

        // Check if the fragment is already in the cache
        var fragment = fragmentCache.get(className.hashCode())
        if (fragment == null) {
            // If not in cache, create a new instance
            fragment = mManager.fragmentFactory.instantiate(mContext.classLoader, className)
            fragmentCache.put(className.hashCode(), fragment)
        }

        // Set arguments for the fragment
        fragment.arguments = args

        // Start the fragment transaction
        val ft = mManager.beginTransaction()

        // Hide other fragments in the container
        val fragments = mManager.fragments
        for (existingFragment in fragments) {
            if (existingFragment != fragment && existingFragment.isAdded) {
                ft.hide(existingFragment)
            }
        }

        // Show the target fragment
        if (!fragment.isAdded) {
            ft.add(mContainerId, fragment, className)
        }
        ft.show(fragment)
        ft.setPrimaryNavigationFragment(fragment)
        ft.setReorderingAllowed(true)

        return ft
    }

    override fun popBackStack(popUpTo: NavBackStackEntry, savedState: Boolean) {
        if (mManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring popBackStack() call: FragmentManager has already saved its state"
            )
            return
        }
        mManager.popBackStack(
            popUpTo.id, FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        state.pop(popUpTo, savedState)
    }
}
