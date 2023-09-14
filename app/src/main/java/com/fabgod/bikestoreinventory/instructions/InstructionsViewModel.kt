package com.fabgod.bikestoreinventory.instructions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InstructionsViewModel : ViewModel() {

    private val _eventButtonClicked = MutableLiveData<Boolean>()
    val eventButtonClicked: LiveData<Boolean> get() = _eventButtonClicked

    fun onButtonClicked() {
        _eventButtonClicked.value = true
    }

    fun onButtonClickedComplete() {
        _eventButtonClicked.value = false
    }
}
