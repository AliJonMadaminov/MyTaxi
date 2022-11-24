package com.example.ui.android.task.junior.models

import com.example.ui.android.task.junior.models.abstractions.BaseTrip
import com.example.ui.android.task.junior.models.trip.Trip
import com.example.ui.android.task.junior.models.user.User
import com.example.ui.android.task.junior.models.user.client.BaseClient

interface ClientDataSource {

    fun getClient():BaseClient

    fun getTrips(client: BaseClient):List<BaseTrip>
}

