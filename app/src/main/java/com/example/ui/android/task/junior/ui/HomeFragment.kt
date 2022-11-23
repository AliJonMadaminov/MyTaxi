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
import com.example.ui.android.task.junior.databinding.FragmentHomeBinding
import com.example.ui.android.task.junior.databinding.NavHeaderBinding
import com.example.ui.android.task.junior.viewmodels.HomeViewModel
import com.example.ui.android.task.junior.viewmodels.HomeViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var myLocationViewOriginal: View

    private var locationPermissionGranted: Boolean = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var viewModel:HomeViewModel

    var mapFragment:SupportMapFragment? = null

    private val callback = OnMapReadyCallback { googleMap ->

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

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showAlertMessageNoGps();
            } else {
                gotoMyLocation(googleMap)
            }
        }
    }

    private fun showAlertMessageNoGps() {
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

            myLocationViewOriginal.callOnClick()
        }
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        NavigationUI.setupWithNavController(binding.navView, findNavController())
        initializeViewModel()
        bindData()
        return binding.root
    }

    private fun bindData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        bindUserData()
    }

    private fun initializeViewModel() {
        val viewModelFactory = HomeViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        myLocationViewOriginal = mapFragment?.requireView()?.findViewById(0x2)!!

        binding.imgHamburger.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
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

    private fun bindUserData() {
        val headerView = binding.navView.getHeaderView(0)
        val navHeaderBinding = NavHeaderBinding.bind(headerView)
        navHeaderBinding.client = viewModel.client
    }
}