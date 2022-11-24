package com.example.ui.android.task.junior.viewmodels

import android.app.Application
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.*
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.models.Client
import com.example.ui.android.task.junior.utils.getCurrentAddress
import com.google.android.gms.maps.model.LatLng

class HomeViewModel(app: Application) : AndroidViewModel(app) {

    val client = Client("Iroda", "Saidova", "+998(93) 000-11-22", R.drawable.girl, listOf())

    val currentLocation: MutableLiveData<LatLng> =
        MutableLiveData(LatLng(41.31114164054522, 69.27959980798161))

    val _currentLocationName: MutableLiveData<String> = MutableLiveData()
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

class HomeViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(app) as T
        }

        throw java.lang.IllegalArgumentException("Unknown class has been passed. Expected: HomeViewModel")
    }

}