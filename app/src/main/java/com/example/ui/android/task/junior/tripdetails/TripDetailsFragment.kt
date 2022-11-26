package com.example.ui.android.task.junior.tripdetails

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.ViewModelProvider
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.databinding.FragmentTripDetailsBinding
import com.example.ui.android.task.junior.models.ZoomLevel
import com.example.ui.android.task.junior.models.trip.BaseTrip
import com.example.ui.android.task.junior.utils.drawableToBitmap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripDetailsFragment() : Fragment() {

    lateinit var viewModel: TripDetailViewModel
    private lateinit var binding: FragmentTripDetailsBinding
    private lateinit var trip: BaseTrip

    private val callback = OnMapReadyCallback { googleMap ->
        val startLocation = trip.startDestination.location
        val endLocation = trip.endDestination.location
        val boundsBuilder = LatLngBounds.Builder()
        val startLocationBitmap =
            drawableToBitmap(getDrawable(requireContext(), R.drawable.from_location))
        val endLocationBitmap =
            drawableToBitmap(getDrawable(requireContext(), R.drawable.to_location))

        setMapStyle(googleMap)
        googleMap.addMarker(
            MarkerOptions().position(startLocation).title(trip.startDestination.name)
                .icon(BitmapDescriptorFactory.fromBitmap(startLocationBitmap!!))
        )
        boundsBuilder.include(startLocation).include(endLocation)
        val padding = 20
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), padding)
        val polylineColor = getColor(requireContext(), R.color.polyline_color)
        googleMap.moveCamera(cameraUpdate)
        googleMap.addPolyline(trip.tripPath.color(polylineColor))
        googleMap.addMarker(
            MarkerOptions().position(endLocation)
                .icon(BitmapDescriptorFactory.fromBitmap(endLocationBitmap!!))
        )
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

    private fun setMapStyle(map: GoogleMap) {
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.map_style
                )
            )

            if (!success) {
                Log.e("MapsActivity: MapStyle", "Map style parsing failed")
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
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