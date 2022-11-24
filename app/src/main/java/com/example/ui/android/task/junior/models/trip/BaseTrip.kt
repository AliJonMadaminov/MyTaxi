package com.example.ui.android.task.junior.models.abstractions

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

abstract class BaseTrip {
    abstract val startDestination:LatLng
    abstract val endDestination:LatLng
    abstract val tripPath:PolylineOptions
    abstract val generalTripInfo:GeneralTripInfo
    abstract val paymentInfo:PaymentInfo
    abstract val driver:BaseDriver
}

abstract class GeneralTripInfo {
    abstract val tripType:TripType
    abstract val paymentMethod:PaymentMethod
    abstract val orderNumber:Int
    abstract val tripStartTime:Long
    abstract val tripEndTime:Long

    abstract fun getTripDuration()
}

abstract class PaymentInfo {
    abstract val minPrice:Int
    abstract val highDemand:Int
    abstract val tripPrice:Int
    abstract val waitPrice:Int

    abstract fun calculateTotalPrice()
}

enum class TripType {
    NORMAL, COMFORT, BUSINESS;

    override fun toString(): String {
        return this.name.lowercase()
    }
}

enum class PaymentMethod {
    CASH, CREDIT_CARD
}