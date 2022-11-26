package com.example.ui.android.task.junior.models.user.client

import com.example.ui.android.task.junior.models.trip.BaseTrip


data class Client(
    override val name: String,
    override val surname: String,
    override val phoneNumber: String,
    override val profileImageRes: Int,

) : BaseClient() {

    override fun getClientFullName(): String {
        return name + "\n" + surname
    }
}