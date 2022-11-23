package com.example.ui.android.task.junior.models.abstractions

import com.example.ui.android.task.junior.models.abstractions.User

abstract class BaseDriver : User(){
    abstract val rating:Rating
    abstract val numberOfDrives:Int
}

enum class Rating(val value:Byte) {
    VERY_BAD_1(1), BAD_2(2), NORMAL_3(3), GOOD_4(4), VERY_GOOD_5(5)
}