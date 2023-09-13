package com.fabgod.bikestoreinventory.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.list.model.Bikes

class DetailsViewModelFactory(private val mode: Int, private val bikeList: Bikes, private val bike: Bike) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(mode, bikeList, bike) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
