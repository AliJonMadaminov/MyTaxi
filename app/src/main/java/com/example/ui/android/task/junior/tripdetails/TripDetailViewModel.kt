package com.example.ui.android.task.junior.tripdetails

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripDetailViewModel @Inject constructor(var repository: TripDetailsRepository) {
}