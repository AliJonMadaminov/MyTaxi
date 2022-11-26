package com.example.ui.android.task.junior.models.trip

import com.example.ui.android.task.junior.models.user.driver.BaseDriver
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

abstract class BaseTrip {
    abstract val startDestination:Destination
    abstract val endDestination:Destination
    abstract val tripPath:PolylineOptions
    abstract val generalTripInfo:BaseGeneralTripInfo
    abstract val paymentInfo:BasePaymentInfo
    abstract val driver:BaseDriver
}

abstract class BaseGeneralTripInfo {
    abstract val tripType:TripType
    abstract val paymentMethod:PaymentMethod
    abstract val orderNumber:Int
    abstract val tripStartTime:Long
    abstract val tripEndTime:Long

    abstract fun getTripDurationString(): String
}

abstract class BasePaymentInfo {
    abstract val minPrice:Int
    abstract val highDemand:Int
    abstract val tripPrice:Int
    abstract val waitPrice:Int

    abstract fun calculateTotalPrice(): Int
}

data class Destination(val name:String, val location:LatLng)

enum class TripType {
    NORMAL, DELIVERY, BUSINESS;

    override fun toString(): String {
        return this.name.lowercase().replaceFirstChar {
            it.uppercase()
        }
    }
}

enum class PaymentMethod {
    CASH, CREDIT_CARD
}