package com.salihkinali.currentnewsapp.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val name: String?,
    val url: String?
):Parcelable