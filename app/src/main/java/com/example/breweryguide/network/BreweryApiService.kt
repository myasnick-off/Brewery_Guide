package com.example.breweryguide.network

import com.example.breweryguide.network.dto.BreweryBasicDto
import com.example.breweryguide.network.dto.BreweryDetailsDto
import com.example.breweryguide.utils.BREWERY_LIST_END_POINT
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BreweryApiService {

    @GET(BREWERY_LIST_END_POINT)
    fun getBreweryList(): Single<List<BreweryBasicDto>>

    @GET("$BREWERY_LIST_END_POINT/{breweryId}")
    fun getBrewery(@Path("breweryId") breweryId: String): Single<BreweryDetailsDto>
}