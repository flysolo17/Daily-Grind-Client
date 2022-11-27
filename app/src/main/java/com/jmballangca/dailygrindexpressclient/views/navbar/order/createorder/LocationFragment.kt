package com.jmballangca.dailygrindexpressclient.views.navbar.order.createorder

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jmballangca.dailygrindexpressclient.R
import com.jmballangca.dailygrindexpressclient.databinding.FragmentLocationBinding


class LocationFragment : Fragment() {
    private lateinit var binding: FragmentLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val callback = OnMapReadyCallback { googleMap ->
        if(!isLocationServiceEnabled()) {
            Toast.makeText(binding.root.context,"Please enable your location service", Toast.LENGTH_SHORT).show()
            return@OnMapReadyCallback
        }
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let {
                    val test = LatLng(it.latitude, it.longitude)
                    googleMap.addMarker(MarkerOptions().position(test).title("Test"))
                    googleMap.setMinZoomPreference(6.0f)
                    googleMap.setMaxZoomPreference(14.0f)
                    googleMap.animateCamera(CameraUpdateFactory.zoomIn())
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(test))
                }
            }
        }

    }
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.context)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_locationFragment_to_dropOffDetailsFragment)
        }
    }


    /**
     * Checks if your gps is enabled
     */
    private fun isLocationServiceEnabled() : Boolean {
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }



}