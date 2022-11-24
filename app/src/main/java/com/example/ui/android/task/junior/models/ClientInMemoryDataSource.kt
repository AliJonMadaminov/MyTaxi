package com.example.ui.android.task.junior.models

import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.models.abstractions.BaseTrip
import com.example.ui.android.task.junior.models.user.client.BaseClient
import com.example.ui.android.task.junior.models.user.client.Client
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientInMemoryDataSource @Inject constructor() : ClientDataSource {

    val client = Client(
        name = "Анастасия",
        surname = "Борисовна",
        phoneNumber = "+998(97) 111-222-333",
        profileImageRes = R.drawable.girl,
        trips = listOf()
    )

    override fun getClient(): BaseClient {
        return client
    }

    override fun getTrips(client: BaseClient): List<BaseTrip> {
        return client.trips
    }

}