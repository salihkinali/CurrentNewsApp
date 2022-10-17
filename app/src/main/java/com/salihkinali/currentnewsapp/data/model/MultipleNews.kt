package com.salihkinali.currentnewsapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class MultipleNews:Parcelable{

     @Parcelize
    data class DetailScreenArgument(val article: Article):Parcelable,MultipleNews()

}
