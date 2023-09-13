package com.fabgod.bikestoreinventory.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.list.model.Bikes

class ListViewModel : ViewModel() {

    private val _list = MutableLiveData<Bikes>()
    val list: LiveData<Bikes> get() = _list

    private val _bikeSelected = MutableLiveData<Bike>()
    val bikeSelected: LiveData<Bike> get() = _bikeSelected

    private val _eventBikeSelected = MutableLiveData<Boolean>()
    val eventBikeSelected: LiveData<Boolean> get() = _eventBikeSelected

    private val _eventAddBike = MutableLiveData<Boolean>()
    val eventAddBike: LiveData<Boolean> get() = _eventAddBike

    private val _eventLogOut = MutableLiveData<Boolean>()
    val eventLogOut: LiveData<Boolean> get() = _eventLogOut

    fun saveList(bikes: Bikes) {
        _list.value = bikes
    }

    fun saveBikeSelected(bike: Bike) {
        _bikeSelected.value = bike
    }

    fun onBikeSelected() {
        _eventBikeSelected.value = true
    }

    fun onBikeSelectedComplete() {
        _eventBikeSelected.value = false
    }

    fun onAddBike() {
        _eventAddBike.value = true
    }

    fun onAddBikeComplete() {
        _eventAddBike.value = false
    }

    fun onLogOut() {
        _eventLogOut.value = true
    }

    fun onLogOutComplete() {
        _eventLogOut.value = false
    }
}
