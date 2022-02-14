package com.example.breweryguide.domain.repository

import com.example.breweryguide.network.BreweryApiService
import com.example.breweryguide.network.dto.BreweryBasicDto
import com.example.breweryguide.network.dto.BreweryDetailsDto
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(private val breweryApiService: BreweryApiService): Repository {

    override fun getBreweryListFromServer(): Single<List<BreweryBasicDto>> {
        return breweryApiService.getBreweryList()
    }

    override fun getBreweryFromServer(breweryId: String): Single<BreweryDetailsDto> {
        return breweryApiService.getBrewery(breweryId)
    }
}