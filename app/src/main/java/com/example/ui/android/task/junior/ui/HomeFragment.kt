package com.example.ui.android.task.junior.ui

import android.Manifest
import com.example.ui.android.task.junior.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.example.ui.android.task.junior.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class HomeFragment : Fragment() {
    private val REQUEST_LOCATION = 123
    private lateinit var binding: FragmentHomeBinding
    private var locationPermissionGranted: Boolean = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private val callback = OnMapReadyCallback { googleMap ->
        val chorsu = LatLng(41.319357679270475, 69.23037889366836)
        val markerDrawable = getDrawable(requireContext(), R.drawable.blue_map_pin)
        val markerIcon =
            markerDrawable?.toBitmap(markerDrawable.intrinsicWidth, markerDrawable.intrinsicHeight)
        var marker: Marker? = googleMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(markerIcon!!))
                .position(chorsu)
        )
        val zoomLevel = 15f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chorsu, zoomLevel))

        setMyLocationListener(googleMap)


        googleMap.setOnCameraMoveListener {
            if (marker != null) {
                marker.position = googleMap.cameraPosition.target
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationListener(googleMap: GoogleMap) {

        binding.imgMyLocation.setOnClickListener {
            gotoMyLocation(googleMap)
        }

    }

    @SuppressLint("MissingPermission")
    private fun gotoMyLocation(googleMap: GoogleMap) {
        if (locationPermissionGranted) {
            if (!googleMap.isMyLocationEnabled) {
                googleMap.isMyLocationEnabled = true
            }

            val mapFragment =
                childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

            val myLocationViewOriginal = mapFragment.requireView().findViewById<View>(0x2)

            if (myLocationViewOriginal != null) {
                myLocationViewOriginal.visibility = View.GONE
                myLocationViewOriginal.callOnClick()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted ->
            if (locationPermissionGranted(isGranted)) {
                locationPermissionGranted = true
            } else {
                convinceProvidingPermission()
            }
        }
        requestPermission(permissionLauncher)
    }

    private fun convinceProvidingPermission() {
        val locationRequestPurpose = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.location_permission_denied))
            .setMessage(getString(R.string.permission_request_explanation))
            .setPositiveButton("allow") { dialogInterface, i ->
                requestPermission(permissionLauncher)
            }
            .create()

        locationRequestPurpose.show()
    }

    private fun requestPermission(permissionLauncher: ActivityResultLauncher<Array<String>>) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

//    private fun requestPermissionIfNecessary() {
//        if (locationPermissionGranted()) {
////            requestPermissions(
////                requireActivity(), arrayOf(
////                    Manifest.permission.ACCESS_COARSE_LOCATION,
////                    Manifest.permission.ACCESS_FINE_LOCATION
////                ),
////                REQUEST_LOCATION
////            )
//
//        } else {
//            Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show()
//
//        }
//    }

    private fun locationPermissionGranted(permissionStatuses: Map<String, Boolean>): Boolean {
        var locationPermissionGranted = true
        for (permissionGranted in permissionStatuses.values) {
            if (!permissionGranted) {
                locationPermissionGranted = false
            }
        }

        return locationPermissionGranted
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}