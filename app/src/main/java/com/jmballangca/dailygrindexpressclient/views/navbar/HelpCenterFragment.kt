package com.jmballangca.dailygrindexpressclient.views.navbar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.jmballangca.dailygrindexpressclient.R
import com.jmballangca.dailygrindexpressclient.adapter.TabAdapter
import com.jmballangca.dailygrindexpressclient.databinding.FragmentHelpCenterBinding


class HelpCenterFragment : Fragment() {
    private lateinit var binding: FragmentHelpCenterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHelpCenterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}