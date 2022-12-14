package com.example.ui.android.task.junior.home

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ui.android.task.junior.models.user.client.BaseClient
import com.example.ui.android.task.junior.home.HomeRepository
import com.example.ui.android.task.junior.utils.getCurrentAddress
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(homeRepository: HomeRepository) : ViewModel() {

    val client: BaseClient = homeRepository.getClient()

    val currentLocation: MutableLiveData<LatLng> =
        MutableLiveData(LatLng(41.31114164054522, 69.27959980798161))

    private val _currentLocationName: MutableLiveData<String> = MutableLiveData()
    val currentLocationName: LiveData<String>
        get() = _currentLocationName


    fun updateCurrentLocationName(geocoder: Geocoder, location: LatLng) {
        val currentAddress = geocoder.getCurrentAddress(location)
        updateCurrentLocationName(currentAddress)
    }

    private fun updateCurrentLocationName(placeName: Address?) {
        if (placeName != null) {

            if (placeName.featureName != null && placeName.subLocality != null) {

                _currentLocationName.postValue(buildString {
                    append(placeName.subLocality)
                    append(", ")
                    append(placeName.featureName)
                })
            }
        }
    }

}