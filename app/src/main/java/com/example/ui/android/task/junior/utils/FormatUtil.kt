package com.example.ui.android.task.junior.utils

import java.text.SimpleDateFormat

fun getFormattedTime(timeInMillis:Long):String {
    return SimpleDateFormat("HH:mm").format(timeInMillis)
}
