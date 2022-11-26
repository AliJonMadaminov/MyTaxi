package com.example.ui.android.task.junior.home

import com.example.ui.android.task.junior.models.ClientDataSource
import com.example.ui.android.task.junior.models.trip.BaseTrip
import com.example.ui.android.task.junior.models.user.client.BaseClient
import javax.inject.Inject

class HomeRepository @Inject constructor(var clientDataSource: ClientDataSource) {


    fun getClient():BaseClient {
        return clientDataSource.getClient()
    }

    fun getTrips():List<BaseTrip> {
        return clientDataSource.getTrips(clientDataSource.getClient())
    }
}