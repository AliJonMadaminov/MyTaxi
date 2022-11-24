package com.example.ui.android.task.junior.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.allPermissionsGranted
import com.example.ui.android.task.junior.databinding.FragmentHomeBinding
import com.example.ui.android.task.junior.databinding.NavHeaderBinding
import com.example.ui.android.task.junior.requestLocationPermission
import com.example.ui.android.task.junior.viewmodels.HomeViewModel
import com.example.ui.android.task.junior.viewmodels.HomeViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var myLocationViewOriginal: View

    private var locationPermissionGranted: Boolean = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var viewModel: HomeViewModel

    private val callback = OnMapReadyCallback { googleMap ->

        gotoMyLocation(googleMap)
        viewModel.updateCurrentLocationNameOnCameIdle(googleMap, Geocoder(requireContext()))

        viewModel.setMinMaxZoomPreferences(googleMap)

        viewModel.initializeMarker(googleMap)

        setMyLocationClickListener(googleMap)

        viewModel.moveMarkerWithCamera(googleMap)

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

        NavigationUI.setupWithNavController(binding.navView, findNavController())
        initializeViewModel()
        bindData()
        return binding.root
    }

    private fun initializeViewModel() {
        val viewModelFactory = HomeViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapAsync(callback)
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

    override fun onDestroy() {
        binding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}