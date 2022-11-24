package com.example.ui.android.task.junior.ui

import androidx.databinding.BindingAdapter
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("imgSource")
fun CircleImageView.setImgSource(imgResource:Int) {
    this.setImageResource(imgResource)
}