package com.example.ui.android.task.junior.tripdetails

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(var repository: TripDetailsRepository):ViewModel() {

    fun getTrip(orderNumber:Int) = repository.getTrip(orderNumber)
}