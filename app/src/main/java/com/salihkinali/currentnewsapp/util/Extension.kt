package com.salihkinali.currentnewsapp.util

import android.view.View
import android.widget.ImageView
import com.salihkinali.currentnewsapp.R
import com.squareup.picasso.Picasso

fun ImageView.downloadImage(url: String?) {
    Picasso.get()
        .load(url)
        .placeholder(R.color.white)
        .error(R.drawable.ic_launcher_background)
        .into(this)
}

fun View.visible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun String.timeConversion(s:String):String{
        val getDate = s.substring(0, 10)
        val getTime = s.substring(11, 16)
        return "Publish Time: $getDate / $getTime"
}

