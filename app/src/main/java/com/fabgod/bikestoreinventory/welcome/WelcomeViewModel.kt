package com.fabgod.bikestoreinventory.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WelcomeViewModel : ViewModel() {

    private val _eventButtonClicked = MutableLiveData<Boolean>()
    val eventButtonClicked: LiveData<Boolean> get() = _eventButtonClicked

    fun onButtonClicked() {
        _eventButtonClicked.value = true
    }

    fun onButtonClickedComplete() {
        _eventButtonClicked.value = false
    }
}
