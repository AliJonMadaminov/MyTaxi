package com.example.ui.android.task.junior.tripdetails

import com.example.ui.android.task.junior.models.ClientDataSource
import com.example.ui.android.task.junior.models.trip.BaseTrip
import javax.inject.Inject

class TripDetailsRepository @Inject constructor(var dataSource: ClientDataSource) {

    fun getTrip(orderNumber:Int):BaseTrip {
        dataSource.getTrips(dataSource.getClient()).forEach { baseTrip ->
            if (baseTrip.generalTripInfo.orderNumber == orderNumber) {
                return baseTrip
            }
        }
        throw java.util.NoSuchElementException("There is no trip with orderNumber:$orderNumber")
    }

}