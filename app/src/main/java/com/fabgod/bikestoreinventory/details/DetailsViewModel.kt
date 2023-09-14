package com.fabgod.bikestoreinventory.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.list.model.Bikes

class DetailsViewModel(mode: Int, bikeList: Bikes?, bike: Bike?) : ViewModel() {

    private val _modelValidationState = MutableLiveData<FieldState>()
    val modelValidationState: LiveData<FieldState> get() = _modelValidationState

    private val _wheelSizeValidationState = MutableLiveData<FieldState>()
    val wheelSizeValidationState: LiveData<FieldState> get() = _wheelSizeValidationState

    private val _colorValidationState = MutableLiveData<FieldState>()
    val colorValidationState: LiveData<FieldState> get() = _colorValidationState

    private val _sizeValidationState = MutableLiveData<FieldState>()
    val sizeValidationState: LiveData<FieldState> get() = _sizeValidationState

    private val _priceValidationState = MutableLiveData<FieldState>()
    val priceValidationState: LiveData<FieldState> get() = _priceValidationState

    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> get() = _isFormValid

    private val _model = MutableLiveData<String>()
    val model: LiveData<String> get() = _model

    private val _wheelSize = MutableLiveData<String>()
    val wheelSize: LiveData<String> get() = _wheelSize

    private val _color = MutableLiveData<String>()
    val color: LiveData<String> get() = _color

    private val _size = MutableLiveData<String>()
    val size: LiveData<String> get() = _size

    private val _price = MutableLiveData<String>()
    val price: LiveData<String> get() = _price

    private val _list = MutableLiveData<Bikes>()
    val list: LiveData<Bikes> get() = _list

    private val _bike = MutableLiveData<Bike>()
    val bike: LiveData<Bike> get() = _bike

    private val _mode = MutableLiveData<Int>()
    val mode: LiveData<Int> get() = _mode

    private val _eventBikeAdded = MutableLiveData<Boolean>()
    val eventBikeAdded: LiveData<Boolean> get() = _eventBikeAdded

    private val _eventBackAction = MutableLiveData<Boolean>()
    val eventBackAction: LiveData<Boolean> get() = _eventBackAction

    init {
        Log.i("LoginViewModel", "Mode and Bike List gotten")
        _mode.value = mode
        _list.value = bikeList ?: Bikes()
        _bike.value = bike ?: Bike()
    }

    fun addBikeToList(bike: Bike) {
        list.value?.bikes?.add(bike)
    }

    fun validateForm() {
        _isFormValid.value = (
            _modelValidationState.value is FieldState.Correct &&
                _wheelSizeValidationState.value is FieldState.Correct &&
                _colorValidationState.value is FieldState.Correct &&
                _sizeValidationState.value is FieldState.Correct &&
                _priceValidationState.value is FieldState.Correct
            )
    }

    fun validateModel(value: String) {
        _modelValidationState.value = if (value.isEmpty()) {
            FieldState.Empty
        } else {
            FieldState.Correct
        }
    }

    fun validateWheelSize(value: String) {
        _wheelSizeValidationState.value = if (value.isEmpty()) {
            FieldState.Empty
        } else {
            FieldState.Correct
        }
    }

    fun validateColor(value: String) {
        _colorValidationState.value = if (value.isEmpty()) {
            FieldState.Empty
        } else {
            FieldState.Correct
        }
    }

    fun validateSize(value: String) {
        _sizeValidationState.value = if (value.isEmpty()) {
            FieldState.Empty
        } else {
            FieldState.Correct
        }
    }

    fun validatePrice(value: String) {
        _priceValidationState.value = if (value.isEmpty()) {
            FieldState.Empty
        } else {
            FieldState.Correct
        }
    }

    fun updateModel(value: String) {
        _model.value = value
    }

    fun updateWheelSize(value: String) {
        _wheelSize.value = value
    }

    fun updateColor(value: String) {
        _color.value = value
    }

    fun updateSize(value: String) {
        _size.value = value
    }

    fun updatePrice(value: String) {
        _price.value = value
    }

    fun onBikeAdded() {
        _eventBikeAdded.value = true
    }

    fun onBikeAddedComplete() {
        _eventBikeAdded.value = false
    }

    fun onBackAction() {
        _eventBackAction.value = true
    }

    fun onBackActionComplete() {
        _eventBackAction.value = false
    }

    sealed class FieldState {
        data object Correct : FieldState()
        data object Empty : FieldState()
    }
}
