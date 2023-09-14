package com.fabgod.bikestoreinventory.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListViewModelFactory(private val drawerLayoutId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(drawerLayoutId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
