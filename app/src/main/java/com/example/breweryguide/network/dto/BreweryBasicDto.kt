package com.example.breweryguide.network.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreweryBasicDto(
    val id: String,
    val name: String,
    val breweryType: String,
    val country: String?
) : Parcelable