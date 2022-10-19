package com.jmballangca.dailygrindexpressclient.adapter

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jmballangca.dailygrindexpressclient.views.navbar.ActiveFragment
import com.jmballangca.dailygrindexpressclient.views.navbar.CompletedFragment
import com.jmballangca.dailygrindexpressclient.views.navbar.OrderFragment
class TabAdapter(
    @NonNull fragmentManager: FragmentManager?,
    @NonNull lifecycle: Lifecycle?
) :
    FragmentStateAdapter(fragmentManager!!,lifecycle!!) {
    @NonNull
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ActiveFragment()
            1 -> CompletedFragment()
            else -> ActiveFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}