package com.example.ui.android.task.junior.models.trip

import com.example.ui.android.task.junior.models.user.driver.BaseDriver
import com.example.ui.android.task.junior.utils.getDurationString
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import javax.inject.Inject

data class Trip @Inject constructor(
    override val startDestination: Destination,
    override val endDestination: Destination,
    override val tripPath: PolylineOptions,
    override val generalTripInfo: BaseGeneralTripInfo,
    override val paymentInfo: BasePaymentInfo,
    override val driver: BaseDriver
) : BaseTrip()


data class GeneralTripInfo(
    override val tripType: TripType,
    override val paymentMethod: PaymentMethod,
    override val orderNumber: Int,
    override val tripStartTime: Long,
    override val tripEndTime: Long
) : BaseGeneralTripInfo() {

    override fun getTripDurationString(): String {
        return getDurationString(tripStartTime, tripEndTime)
    }
}

data class PaymentInfo(
    override val minPrice: Int,
    override val highDemand: Int,
    override val tripPrice: Int,
    override val waitPrice: Int
) : BasePaymentInfo() {

    override fun calculateTotalPrice(): Int {
        return tripPrice + highDemand + waitPrice
    }

}
