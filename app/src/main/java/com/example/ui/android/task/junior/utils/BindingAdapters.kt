package com.example.ui.android.task.junior.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("imgSource")
fun CircleImageView.setImgSource(imgResource:Int) {
    this.setImageResource(imgResource)
}

@BindingAdapter("time")
fun TextView.setTimeFormatted(time:Long) {
    text = getFormattedTime(time)
}

@BindingAdapter("priceAndCurrency", requireAll = true)
fun TextView.setPriceFormatted(price:Int, currency:String) {
    var priceWithoutSpaces = price.toString()
    var priceFormatted = ""

    for (i in priceWithoutSpaces.length - 1 downTo 0) {
        priceFormatted = priceWithoutSpaces.get(i) + priceFormatted
        if ((i + 1) % 3 == 0) {
            priceFormatted = " $priceFormatted"
        }
    }
     text = buildString {
        append(priceFormatted)
        append(" $currency")
    }
}