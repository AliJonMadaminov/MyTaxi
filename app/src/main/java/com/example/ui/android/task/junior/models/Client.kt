package com.example.ui.android.task.junior.models

import android.graphics.drawable.Drawable
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.models.abstractions.BaseClient
import com.example.ui.android.task.junior.models.abstractions.BaseTrip

data class Client(
    override val name: String,
    override val surname: String,
    override val phoneNumber: String,
    override val profileImageRes: Int,
    override val trips: List<BaseTrip>

) : BaseClient() {

    fun getClientFullName(): String {
        return name + "\n" + surname
    }
}