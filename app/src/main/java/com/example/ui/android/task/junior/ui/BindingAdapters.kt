package com.example.ui.android.task.junior.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.ui.android.task.junior.models.Client
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("imgSource")
fun CircleImageView.setImgSource(imgResource:Int) {
    this.setImageResource(imgResource)
}