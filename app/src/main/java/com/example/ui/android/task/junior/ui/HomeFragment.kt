package com.example.ui.android.task.junior.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Insets
import android.graphics.drawable.Drawable
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.*
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.allPermissionsGranted
import com.example.ui.android.task.junior.databinding.FragmentHomeBinding
import com.example.ui.android.task.junior.databinding.NavHeaderBinding
import com.example.ui.android.task.junior.models.ZoomLevel
import com.example.ui.android.task.junior.requestLocationPermission
import com.example.ui.android.task.junior.utils.addMarkerIfNecessary
import com.example.ui.android.task.junior.utils.setMinMaxZoomPreferences
import com.example.ui.android.task.junior.viewmodels.HomeViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private lateinit var binding: FragmentHomeBinding
    private lateinit var myLocationViewOriginal: View
    private var marker: Marker? = null


    private var locationPermissionGranted: Boolean = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    lateinit var viewModel: HomeViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        val markerIcon = getDrawable(requireContext(), R.drawable.blue_map_pin)
        val geocoder = Geocoder(requireContext())
        initializeMarker(googleMap, markerIcon)
        // Because of this method, go to my location not working
        observeCurrentLocation()
        moveMarkerWithCamera(googleMap)
        updateLocationNameOnCameraIdle(googleMap, geocoder)
        googleMap.setMinMaxZoomPreferences()
        setMyLocationClickListener(googleMap)
        gotoMyLocation(googleMap)
    }

    private fun initializeMarker(
        googleMap: GoogleMap,
        markerIcon: Drawable?
    ) {
        viewModel.currentLocation.value?.let {
            marker = googleMap.addMarkerIfNecessary(marker, markerIcon, it)
        }
    }

    private fun observeCurrentLocation() {
        viewModel.currentLocation.observe(viewLifecycleOwner) { currentLocation ->
            marker?.position = currentLocation
        }
    }

    fun moveMarkerWithCamera(googleMap: GoogleMap) {
        googleMap.setOnCameraMoveListener {
            if (marker != null) {
                uiScope.launch {
                    viewModel.currentLocation.postValue(googleMap.cameraPosition.target)
                }
            }
        }
    }

    private fun updateLocationNameOnCameraIdle(
        googleMap: GoogleMap,
        geocoder: Geocoder
    ) {
        googleMap.setOnCameraIdleListener {
            viewModel.updateCurrentLocationName(geocoder, googleMap.cameraPosition.target)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationClickListener(googleMap: GoogleMap) {
        binding.imgMyLocation.setOnClickListener {
            val manager: LocationManager =
                getSystemService(requireContext(), LocationManager::class.java) as LocationManager

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                gotoMyLocation(googleMap)
            } else {
                showAlertMessageNoGps();
            }
        }
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

    private fun showAlertMessageNoGps() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.enable_gps_message))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(
                getString(R.string.no)
            ) { dialog, id -> dialog.cancel() }

        val alert = builder.create()
        alert.show()
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
        permissionLauncher.requestLocationPermission()
    }

    private fun locationPermissionGranted(permissionStatuses: Map<String, Boolean>): Boolean {
        var locationPermissionGranted = true
        if (!permissionStatuses.allPermissionsGranted()) {
            locationPermissionGranted = false
        }
        return locationPermissionGranted
    }

    private fun convinceProvidingPermission() {
        val locationRequestPurpose = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.location_permission_denied))
            .setMessage(getString(R.string.permission_request_explanation))
            .setPositiveButton("allow") { dialogInterface, i ->
                permissionLauncher.requestLocationPermission()
            }
            .create()

        locationRequestPurpose.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(callback)

        NavigationUI.setupWithNavController(binding.navView, findNavController())
        initializeViewModel()
        bindData()
        return binding.root
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun bindData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        bindUserData()
    }

    private fun bindUserData() {
        val headerView = binding.navView.getHeaderView(0)
        val navHeaderBinding = NavHeaderBinding.bind(headerView)
        navHeaderBinding.client = viewModel.client
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myLocationViewOriginal = binding.mapView.findViewById(0x2)!!
        binding.imgHamburger.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroyView() {
        binding.mapView.onDestroy()
        marker?.remove()
        marker = null
        super.onDestroyView()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}