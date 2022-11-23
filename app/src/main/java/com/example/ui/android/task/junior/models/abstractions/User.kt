package com.example.ui.android.task.junior.models.abstractions

import android.graphics.drawable.Drawable

abstract class User {
    abstract val name:String
    abstract val surname:String
    abstract val phoneNumber:String

    // In real app it would be an image URI.
    abstract val profileImageRes:Int
}