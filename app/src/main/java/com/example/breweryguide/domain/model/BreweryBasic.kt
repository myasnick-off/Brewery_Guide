package com.example.breweryguide.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreweryBasic(
    val id: String,
    val name: String,
    val breweryType: String,
    val country: String
) : Parcelable