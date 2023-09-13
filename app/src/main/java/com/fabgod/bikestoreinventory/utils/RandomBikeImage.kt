package com.fabgod.bikestoreinventory.utils

import com.fabgod.bikestoreinventory.R

fun getRandomBikeImageResource(): Int {
    val bikeResourceList = mutableListOf(
        R.drawable.random_bike_image_1,
        R.drawable.random_bike_image_2,
        R.drawable.random_bike_image_3,
        R.drawable.random_bike_image_4,
        R.drawable.random_bike_image_5,
        R.drawable.random_bike_image_6,
    )
    bikeResourceList.shuffle()
    return bikeResourceList[0]
}

