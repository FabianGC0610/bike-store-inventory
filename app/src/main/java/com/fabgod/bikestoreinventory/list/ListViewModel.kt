package com.fabgod.bikestoreinventory.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fabgod.bikestoreinventory.list.model.Bike

class ListViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Bike>>()
    val list: LiveData<List<Bike>> get() = _list
}
