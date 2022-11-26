package com.example.ui.android.task.junior.utils

import java.text.SimpleDateFormat

fun getFormattedDateAndTime(dateInMillis: Long):String {
    return SimpleDateFormat("dd MMMM, HH:mm").format(dateInMillis)
}

fun getFormattedTime(timeInMillis: Long): String {
    return SimpleDateFormat("HH:mm").format(timeInMillis)
}

fun getFormattedDate(dateInMillis: Long): String {
    return SimpleDateFormat("d MMMM, EEEE").format(dateInMillis)
}

fun sameDay(firstTimeInMillis:Long, secondTimeInMillis:Long):Boolean {
    val dateFormat = SimpleDateFormat("yyyy MM dd")
    return dateFormat.format(firstTimeInMillis).equals(dateFormat.format(secondTimeInMillis))
}