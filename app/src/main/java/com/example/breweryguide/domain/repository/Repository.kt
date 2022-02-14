package com.example.breweryguide.domain.repository

import com.example.breweryguide.network.dto.BreweryBasicDto
import com.example.breweryguide.network.dto.BreweryDetailsDto
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun getBreweryListFromServer():Single<List<BreweryBasicDto>>
    fun getBreweryFromServer(breweryId: String):Single<BreweryDetailsDto>
}