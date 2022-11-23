package com.example.ui.android.task.junior.models.abstractions

import com.example.ui.android.task.junior.models.abstractions.BaseTrip

abstract class BaseClient : User() {

    abstract val trips:List<BaseTrip>

}
