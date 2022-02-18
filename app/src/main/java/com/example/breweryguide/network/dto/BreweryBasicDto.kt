package com.example.breweryguide.network.dto

data class BreweryBasicDto(
    val id: String,
    val name: String,
    val breweryType: String,
    val country: String?
)