package com.fabgod.bikestoreinventory.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel(sessionSaved: Boolean) : ViewModel() {

    private val _isSessionSaved = MutableLiveData<Boolean>()
    val isSessionSaved: LiveData<Boolean> get() = _isSessionSaved

    private val _eventSessionSaved = MutableLiveData<Boolean>()
    val eventSessionSaved: LiveData<Boolean> get() = _eventSessionSaved

    init {
        Log.i("LoginViewModel", "Session gotten")
        _isSessionSaved.value = sessionSaved
    }

    fun onLogIn() {
        _eventSessionSaved.value = true
    }

    fun onLogInCompleteComplete() {
        _eventSessionSaved.value = false
    }
}
