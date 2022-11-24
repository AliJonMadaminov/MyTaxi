package com.example.ui.android.task.junior.models.user.client

import com.example.ui.android.task.junior.models.trip.BaseTrip
import com.example.ui.android.task.junior.models.user.User
import javax.inject.Inject

abstract class BaseClient() : User() {

    val trips:MutableList<BaseTrip> = mutableListOf()

    abstract fun getClientFullName():String

}
