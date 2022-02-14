package com.example.breweryguide.utils

import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.domain.model.BreweryDetails
import com.example.breweryguide.network.dto.BreweryBasicDto
import com.example.breweryguide.network.dto.BreweryDetailsDto

fun dtoToModelBasicConvertor(breweryBasicDto: BreweryBasicDto): BreweryBasic {
    return BreweryBasic(
        id = breweryBasicDto.id,
        name = breweryBasicDto.name,
        breweryType = breweryBasicDto.breweryType,
        country = breweryBasicDto.country.orEmpty()
    )
}

fun dtoToModelDetailsConvertor(breweryDetailsDto: BreweryDetailsDto): BreweryDetails {
    return BreweryDetails(
        id = breweryDetailsDto.id,
        name = breweryDetailsDto.name,
        breweryType = breweryDetailsDto.breweryType,
        street = breweryDetailsDto.street.orEmpty(),
        address2 = breweryDetailsDto.address2.orEmpty(),
        address3 = breweryDetailsDto.address3.orEmpty(),
        city = breweryDetailsDto.city.orEmpty(),
        state = breweryDetailsDto.state.orEmpty(),
        countyProvince = breweryDetailsDto.countyProvince.orEmpty(),
        postalCode = breweryDetailsDto.postalCode.orEmpty(),
        country = breweryDetailsDto.country.orEmpty(),
        longitude = breweryDetailsDto.longitude?.toDouble(),
        latitude = breweryDetailsDto.latitude?.toDouble(),
        phone = breweryDetailsDto.phone.orEmpty(),
        websiteUrl = breweryDetailsDto.websiteUrl.orEmpty(),
        updatedAt = if (breweryDetailsDto.updatedAt != null) breweryDetailsDto.updatedAt.split("T").first() else "",
        createdAt = if (breweryDetailsDto.createdAt != null) breweryDetailsDto.createdAt.split("T").first() else ""
    )
}