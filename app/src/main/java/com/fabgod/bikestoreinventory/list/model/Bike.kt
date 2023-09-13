package com.fabgod.bikestoreinventory.list.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bike(
    val model: String = "",
    val wheelSize: String = "",
    val color: String = "",
    val size: String = "",
    val price: String = "",
    val imageResource: Int = 0,
) : Parcelable
