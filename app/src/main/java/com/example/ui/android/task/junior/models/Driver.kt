package com.example.ui.android.task.junior.models

import android.graphics.drawable.Drawable
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.models.abstractions.BaseDriver
import com.example.ui.android.task.junior.models.abstractions.Rating

data class Driver(
    override val name: String,
    override val surname: String,
    override val phoneNumber: String,
    override val profileImageRes: Int,
    override val rating: Rating,
    override val numberOfDrives: Int
) : BaseDriver()