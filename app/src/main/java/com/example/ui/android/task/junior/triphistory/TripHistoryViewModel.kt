package com.example.ui.android.task.junior.triphistory

import androidx.lifecycle.ViewModel
import com.example.ui.android.task.junior.models.trip.BaseTrip
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripHistoryViewModel @Inject constructor(var repository: TripHistoryRepository) : ViewModel(){
    fun getTrips():List<BaseTrip> = repository.trips
}