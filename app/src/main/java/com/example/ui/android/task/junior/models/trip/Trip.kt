package com.example.ui.android.task.junior.models.trip

import com.example.ui.android.task.junior.models.user.driver.BaseDriver
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import javax.inject.Inject

data class Trip @Inject constructor(
    override val startDestination: LatLng,
    override val endDestination: LatLng,
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

    override fun getTripDuration():Long {
        return tripEndTime - tripStartTime
    }
}

data class PaymentInfo(
    override val minPrice: Int,
    override val highDemand: Int,
    override val tripPrice: Int,
    override val waitPrice: Int
) : BasePaymentInfo() {

    override fun calculateTotalPrice():Int {
        return tripPrice + highDemand + waitPrice
    }

}
