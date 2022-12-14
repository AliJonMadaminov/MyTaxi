package com.example.ui.android.task.junior.triphistory

import com.example.ui.android.task.junior.models.ClientDataSource
import javax.inject.Inject

class TripHistoryRepository @Inject constructor(dataSource: ClientDataSource) {
    val trips = dataSource.getTrips(dataSource.getClient())
}