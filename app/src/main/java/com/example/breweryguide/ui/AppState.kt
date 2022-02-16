package com.example.breweryguide.ui

import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.domain.model.BreweryDetails

sealed class AppState {
    object Loading : AppState()
    object ListSuccess: AppState()
    data class DetailsSuccess(val breweryDetails: BreweryDetails): AppState()
    data class Error(val error: Throwable): AppState()
}