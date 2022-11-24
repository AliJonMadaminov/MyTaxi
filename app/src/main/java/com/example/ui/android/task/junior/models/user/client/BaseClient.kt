package com.example.ui.android.task.junior.models.user.client

import com.example.ui.android.task.junior.models.abstractions.BaseTrip
import com.example.ui.android.task.junior.models.user.User
import javax.inject.Inject

abstract class BaseClient @Inject constructor() : User() {

    abstract val trips:List<BaseTrip>

}
