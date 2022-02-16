package com.example.breweryguide.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BreweriesViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BreweriesViewModel() as T
    }
}