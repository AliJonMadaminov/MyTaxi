package com.example.ui.android.task.junior.tripdetails

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.databinding.FragmentTripDetailsBinding
import com.example.ui.android.task.junior.models.ZoomLevel
import com.example.ui.android.task.junior.models.trip.BaseTrip

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripDetailsFragment() : Fragment() {

    lateinit var viewModel: TripDetailViewModel
    private lateinit var binding:FragmentTripDetailsBinding
    private lateinit var trip:BaseTrip

    private val callback = OnMapReadyCallback { googleMap ->
        val startLocation = trip.startDestination.location
        val endLocation = trip.endDestination.location

        googleMap.addMarker(MarkerOptions().position(startLocation).title(trip.startDestination.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, ZoomLevel.STREETS.value - 3))
        googleMap.addPolyline(trip.tripPath)
        googleMap.addMarker(MarkerOptions().position(endLocation))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[TripDetailViewModel::class.java]
        val args = TripDetailsFragmentArgs.fromBundle(requireArguments())
        trip = viewModel.getTrip(args.orderNumber)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTripDetailsBinding.inflate(inflater)
        binding.mapTripDetail.onCreate(savedInstanceState)
        binding.mapTripDetail.getMapAsync(callback)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.mapTripDetail.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapTripDetail.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapTripDetail.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapTripDetail.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapTripDetail.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapTripDetail.onLowMemory()
    }
}