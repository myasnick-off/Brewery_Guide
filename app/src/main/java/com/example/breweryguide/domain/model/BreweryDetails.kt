package com.example.breweryguide.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreweryDetails(
    val id: String,
    val name: String,
    val breweryType: String,
    val street: String,
    val address2: String,
    val address3: String,
    val city: String,
    val state: String,
    val countyProvince: String,
    val postalCode: String,
    val country: String,
    val longitude: Double?,
    val latitude: Double?,
    val phone: String,
    val websiteUrl: String,
    val updatedAt: String,
    val createdAt: String
) : Parcelable