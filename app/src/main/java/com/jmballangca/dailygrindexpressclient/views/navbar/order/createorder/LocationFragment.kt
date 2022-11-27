package com.jmballangca.dailygrindexpressclient.views.navbar.order.createorder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jmballangca.dailygrindexpressclient.R
import com.jmballangca.dailygrindexpressclient.databinding.FragmentDropOffDetailsBinding
import com.jmballangca.dailygrindexpressclient.databinding.FragmentLocationBinding


class LocationFragment : Fragment() {
    private lateinit var binding: FragmentLocationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLocationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_dropOffDetailsFragment)
        }
    }
}