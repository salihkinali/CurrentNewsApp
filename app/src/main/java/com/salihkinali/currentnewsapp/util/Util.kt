package com.salihkinali.currentnewsapp.util

import android.view.View
import android.widget.ImageView
import com.salihkinali.currentnewsapp.R
import com.squareup.picasso.Picasso

fun ImageView.downloadImage(url: String?) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
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

