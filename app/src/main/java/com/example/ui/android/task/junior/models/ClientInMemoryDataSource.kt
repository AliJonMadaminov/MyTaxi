package com.example.ui.android.task.junior.models

import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.models.trip.*
import com.example.ui.android.task.junior.models.user.client.BaseClient
import com.example.ui.android.task.junior.models.user.client.Client
import com.example.ui.android.task.junior.models.user.driver.Car
import com.example.ui.android.task.junior.models.user.driver.Driver
import com.example.ui.android.task.junior.models.user.driver.Rating
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientInMemoryDataSource @Inject constructor() : ClientDataSource {

    private val client = Client(
        name = "Анастасия",
        surname = "Борисовна",
        phoneNumber = "+998(97) 111-222-333",
        profileImageRes = R.drawable.girl,
    )

    init {
        val startDestination = Destination("Rakat mahalla, Yakkasaroy",LatLng(41.29331806639822, 69.24530385741546))
        val endDestination = Destination("Mega Planet, Yunusobod",LatLng(41.36726047554213, 69.29100737678553))
        val tripPath = PolylineOptions()
            .add(startDestination.location)
            .add(LatLng(41.304380412956036, 69.2469597559426))
            .add(LatLng(41.32617005974921, 69.24455649670386))
            .add(LatLng(41.352076073030915, 69.2651558616074))
            .add(endDestination.location)

        client.trips.add(
            Trip(
                startDestination,
                endDestination,
                tripPath,
                GeneralTripInfo(
                    TripType.BUSINESS,
                    PaymentMethod.CASH,
                    152,
                    1669300853000,
                    1669304453000
                ),
                PaymentInfo(5000, 3000, 20000, 0),
                Driver(
                    "Alisher",
                    "Botirov",
                    "+998(94) 666-77-88",
                    R.drawable.driver_man,
                    Rating.VERY_GOOD_5,
                    1000,
                    Car("Белый Сhevrolet Malibu", "M 456 C", 45)
                )
            )
        )

        client.trips.add(
            Trip(
                startDestination = Destination("Mega Planet, Yunusobod",LatLng(41.36726047554213, 69.29100737678553)),
                endDestination = Destination("Rakat mahalla, Yakkasaroy",LatLng(41.29331806639822, 69.24530385741546)),
                tripPath,
                GeneralTripInfo(
                    TripType.NORMAL,
                    PaymentMethod.CASH,
                    154,
                    1669311030000,
                    1669314030000
                ),
                PaymentInfo(5000, 5000, 25000, 2000),
                Driver(
                    "Qosim",
                    "Eshmatov",
                    "+998(93) 555-66-77",
                    R.drawable.driver_man,
                    Rating.GOOD_4,
                    589,
                    Car("Белый Сhevrolet Malibu", "M 756 D", 40)
                )
            )
        )

        client.trips.add(
            Trip(
                startDestination,
                endDestination,
                tripPath,
                GeneralTripInfo(
                    TripType.BUSINESS,
                    PaymentMethod.CASH,
                    155,
                    1669382455000,
                    1669386055000
                ),
                PaymentInfo(5000, 3000, 20000, 0),
                Driver(
                    "Alisher",
                    "Botirov",
                    "+998(94) 666-77-88",
                    R.drawable.driver_man,
                    Rating.VERY_GOOD_5,
                    1000,
                    Car("Чёрный Сhevrolet Malibu", "K 456 Q", 78)
                )
            )
        )

        client.trips.add(
            Trip(
                startDestination,
                endDestination,
                tripPath,
                GeneralTripInfo(
                    TripType.BUSINESS,
                    PaymentMethod.CASH,
                    156,
                    1669382455000,
                    1669386055000
                ),
                PaymentInfo(5000, 3000, 20000, 0),
                Driver(
                    "Alisher",
                    "Botirov",
                    "+998(94) 666-77-88",
                    R.drawable.driver_man,
                    Rating.VERY_GOOD_5,
                    1000,
                    Car("Чёрный Сhevrolet Malibu", "E 486 W",32)
                )
            )
        )

        client.trips.add(
            Trip(
                startDestination = Destination("Mega Planet, Yunusobod",LatLng(41.36726047554213, 69.29100737678553)),
                endDestination = Destination("Rakat mahalla, Yakkasaroy",LatLng(41.29331806639822, 69.24530385741546)),
                tripPath,
                GeneralTripInfo(
                    TripType.NORMAL,
                    PaymentMethod.CASH,
                    174,
                    1669382455000,
                    1669386055000
                ),
                PaymentInfo(5000, 5000, 25000, 2000),
                Driver(
                    "Qosim",
                    "Eshmatov",
                    "+998(93) 555-66-77",
                    R.drawable.driver_man,
                    Rating.GOOD_4,
                    589,
                    Car("Белый Сhevrolet cobalt", "M 126 P", 78)
                )
            )
        )
    }

    override fun getClient(): BaseClient {
        return client
    }

    override fun getTrips(client: BaseClient): MutableList<BaseTrip> {
        return client.trips
    }

}