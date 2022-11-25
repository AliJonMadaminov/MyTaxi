package com.example.ui.android.task.junior.home

import com.example.ui.android.task.junior.models.ClientDataSource
import javax.inject.Inject

class HomeRepository @Inject constructor(var clientDataSource: ClientDataSource) {


    fun getClient() {
        clientDataSource.getClient()
    }

    fun getTrips() {
        clientDataSource.getTrips(clientDataSource.getClient())
    }
}