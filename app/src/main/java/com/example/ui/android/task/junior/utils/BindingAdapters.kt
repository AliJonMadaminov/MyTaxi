package com.example.ui.android.task.junior.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ui.android.task.junior.R
import com.example.ui.android.task.junior.models.trip.TripType
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("imgSource")
fun CircleImageView.setImgSource(imgResource:Int) {
    this.setImageResource(imgResource)
}

@BindingAdapter("carImage")
fun ImageView.setCarImage(tripType: TripType) {
    val imgRes = when(tripType) {
        TripType.NORMAL -> R.drawable.taxi
        TripType.DELIVERY -> R.drawable.delivery
        else -> R.drawable.business
    }
    setImageResource(imgRes)
}

@BindingAdapter("time")
fun TextView.setTimeFormatted(time:Long) {
    text = getFormattedTime(time)
}

@BindingAdapter("formattedDate")
fun TextView.setDateFormatted(dateInMillis:Long) {
    text = getFormattedDate(dateInMillis)
}

@BindingAdapter(value = ["bind:price", "bind:currency"], requireAll = false)
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
