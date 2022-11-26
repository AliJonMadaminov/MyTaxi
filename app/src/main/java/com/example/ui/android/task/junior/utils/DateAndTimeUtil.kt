package com.example.ui.android.task.junior.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun getFormattedDateAndTime(dateInMillis: Long): String {
    return SimpleDateFormat("dd MMMM, HH:mm").format(dateInMillis)
}

fun getFormattedTime(timeInMillis: Long): String {
    return SimpleDateFormat("HH:mm").format(timeInMillis)
}

fun getFormattedDate(dateInMillis: Long): String {
    return SimpleDateFormat("d MMMM, EEEE").format(dateInMillis)
}

fun getDurationString(startTime: Long, endTime: Long): String {
    val formatter = SimpleDateFormat("HH:mm")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val duration = endTime - startTime
    return formatter.format(duration)
}

fun sameDay(firstTimeInMillis: Long, secondTimeInMillis: Long): Boolean {
    val dateFormat = SimpleDateFormat("yyyy MM dd")
    return dateFormat.format(firstTimeInMillis).equals(dateFormat.format(secondTimeInMillis))
}