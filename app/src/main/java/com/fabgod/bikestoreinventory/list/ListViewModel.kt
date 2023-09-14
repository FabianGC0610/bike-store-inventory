package com.fabgod.bikestoreinventory.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.list.model.Bikes

class ListViewModel(drawerLayoutId: Int) : ViewModel() {

    private val _list = MutableLiveData<Bikes>()
    val list: LiveData<Bikes> get() = _list

    private val _drawerLayoutId = MutableLiveData<Int>()
    val drawerLayoutId: LiveData<Int> get() = _drawerLayoutId

    private val _bikeSelected = MutableLiveData<Bike>()
    val bikeSelected: LiveData<Bike> get() = _bikeSelected

    private val _eventBikeSelected = MutableLiveData<Boolean>()
    val eventBikeSelected: LiveData<Boolean> get() = _eventBikeSelected

    private val _eventAddBike = MutableLiveData<Boolean>()
    val eventAddBike: LiveData<Boolean> get() = _eventAddBike

    private val _eventOpenMenu = MutableLiveData<Boolean>()
    val eventOpenMenu: LiveData<Boolean> get() = _eventOpenMenu

    init {
        Log.i("ListViewModel", "Drawer Layout Id gotten")
        _drawerLayoutId.value = drawerLayoutId
    }

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

    fun onOpenMenu() {
        _eventOpenMenu.value = true
    }

    fun onOpenMenuComplete() {
        _eventOpenMenu.value = false
    }
}
