package com.example.ui.android.task.junior.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Rect
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.databinding.FragmentHomeBinding
import com.example.ui.android.task.junior.models.ZoomLevel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mapFragment: Fragment
    private lateinit var myLocationViewOriginal: View

    private var locationPermissionGranted: Boolean = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private val callback = OnMapReadyCallback { googleMap ->
        val amirTemurSquare = LatLng(41.31114164054522, 69.27959980798161)
        val markerDrawable = getDrawable(requireContext(), R.drawable.blue_map_pin)
        val markerIcon =
            markerDrawable?.toBitmap(markerDrawable.intrinsicWidth, markerDrawable.intrinsicHeight)
        var marker: Marker? = googleMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(markerIcon!!))
                .position(amirTemurSquare)
        )
        googleMap.setOnCameraIdleListener {
            val geocoder = Geocoder(requireContext())
            val placeNames = geocoder.getFromLocation(
                googleMap.cameraPosition.target.latitude,
                googleMap.cameraPosition.target.longitude,
                2
            )

            val placeName = placeNames[0]
            if (placeName != null && placeName.featureName != null && placeName.subLocality != null) {

                binding.bottomSheetHome.txtStartDestination.text = buildString {
                    append(placeName.subLocality)
                    append(", ")
                    append(placeName.featureName)
                }

            }
        }

        googleMap.setMinZoomPreference(ZoomLevel.CITY.value)
        googleMap.setMaxZoomPreference(ZoomLevel.BUILDINGS.value)

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                amirTemurSquare,
                ZoomLevel.STREETS.value
            )
        )

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

            val manager: LocationManager =
                getSystemService(requireContext(), LocationManager::class.java) as LocationManager

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
            } else {
                gotoMyLocation(googleMap)
            }

        }

    }

    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }

        val alert = builder.create()
        alert.show()
    }

    @SuppressLint("MissingPermission")
    private fun gotoMyLocation(googleMap: GoogleMap) {
        if (locationPermissionGranted) {
            if (!googleMap.isMyLocationEnabled) {
                googleMap.isMyLocationEnabled = true
                myLocationViewOriginal.visibility = View.GONE
            }

            myLocationViewOriginal.performClick()
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
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        myLocationViewOriginal = mapFragment.requireView().findViewById(0x2)

        NavigationUI.setupWithNavController(binding.navView, findNavController())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        binding.imgHamburger.setOnClickListener {
            binding.root.openDrawer(GravityCompat.START)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}