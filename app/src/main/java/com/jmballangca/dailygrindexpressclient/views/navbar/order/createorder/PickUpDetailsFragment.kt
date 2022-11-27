package com.jmballangca.dailygrindexpressclient.views.navbar.order.createorder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jmballangca.dailygrindexpressclient.R
import com.jmballangca.dailygrindexpressclient.databinding.FragmentPickUpDetailsBinding
import java.security.Permissions
import java.util.*


class PickUpDetailsFragment : Fragment() {
    private lateinit var binding : FragmentPickUpDetailsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            val location = p0.lastLocation
            location?.let {
                binding.textAddress.text = getLocationInfo(it.latitude,it.longitude)
            }
        }
    }
    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    findNavController().navigate(R.id.action_pickUpDetailsFragment_to_locationFragment)
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)-> {
                    findNavController().navigate(R.id.action_pickUpDetailsFragment_to_locationFragment)
                } else -> {
                    Toast.makeText(binding.root.context,"Permission Denied!",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPickUpDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.context)
        binding.textAddress.setOnClickListener {
            if (checkPermission()) {
                findNavController().navigate(R.id.action_pickUpDetailsFragment_to_locationFragment)
                return@setOnClickListener
            }
            requestPermissions()
        }
        binding.buttonGetLocation.setOnClickListener {
            getLastLocation()
        }
        binding.buttonNext.setOnClickListener {
            if (!binding.textAddress.equals("No Address")) {
                findNavController().navigate(R.id.action_pickUpDetailsFragment_to_dropOffDetailsFragment)
            }
        }
    }

    private fun getLastLocation() {
        if(!isLocationServiceEnabled()) {
            Toast.makeText(binding.root.context,"Please enable your location service",Toast.LENGTH_SHORT).show()
            return
        }

        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result == null) {
                    getNewLocation()
                    return@addOnCompleteListener
                }
                task.result?.let {
                    binding.textAddress.text = getLocationInfo(it.latitude,it.longitude)
                }
            }
        }
    }

    private fun getNewLocation() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 0)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(0)
            .setMaxUpdateDelayMillis(2)
            .build()
    }

    private fun getLocationInfo(latitude : Double ,longitude : Double) : String{
        val geocoder = Geocoder(binding.root.context, Locale.getDefault())
        val address = geocoder.getFromLocation(latitude,longitude,1)
        return address[0].getAddressLine(0)
    }

    private fun checkPermission() : Boolean{
        if (ActivityCompat.checkSelfPermission(binding.root.context,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(binding.root.context,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    /**
     * Checks if your gps is enabled
     */
    private fun isLocationServiceEnabled() : Boolean {
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }



}