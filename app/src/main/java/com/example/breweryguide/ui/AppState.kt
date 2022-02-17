package com.example.breweryguide.ui

import com.example.breweryguide.domain.model.BreweryDetails

// запечатанный класс с подклассами-состояниями приложения
sealed class AppState {
    object Loading : AppState()
    object ListSuccess: AppState()
    data class DetailsSuccess(val breweryDetails: BreweryDetails): AppState()
    data class Error(val error: Throwable): AppState()
}