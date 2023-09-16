package com.fabgod.bikestoreinventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.list.model.Bikes

class MainActivityViewModel : ViewModel() {

    private val _list = MutableLiveData<Bikes>()
    val list: LiveData<Bikes> get() = _list

    fun saveList(bikes: Bikes) {
        _list.value = bikes
    }

    fun addBikeToList(bike: Bike) {
        _list.value?.bikes?.add(bike)
    }
}
