package com.example.ui.android.task.junior.models.user.driver

data class Driver(
    override val name: String,
    override val surname: String,
    override val phoneNumber: String,
    override val profileImageRes: Int,
    override val rating: Rating,
    override val numberOfDrives: Int
) : BaseDriver()