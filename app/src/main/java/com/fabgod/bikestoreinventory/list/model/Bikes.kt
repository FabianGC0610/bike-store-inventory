package com.fabgod.bikestoreinventory.list.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bikes(
    val bikes: MutableList<Bike>? = null,
) : Parcelable
