package com.example.ui.android.task.junior.models

import com.example.ui.android.task.junior.models.abstractions.BaseDriver
import com.example.ui.android.task.junior.models.abstractions.BaseTrip
import com.example.ui.android.task.junior.models.abstractions.GeneralTripInfo
import com.example.ui.android.task.junior.models.abstractions.PaymentInfo
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions

data class Trip(
    override val startDestination: LatLng,
    override val endDestination: LatLng,
    override val tripPath: PolylineOptions,
    override val generalTripInfo: GeneralTripInfo,
    override val paymentInfo: PaymentInfo,
    override val driver: BaseDriver
) : BaseTrip()
