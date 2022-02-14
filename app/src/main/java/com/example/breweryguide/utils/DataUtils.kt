package com.example.breweryguide.utils

import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.network.dto.BreweryBasicDto

fun dtoToModelBasicConvertor(breweryBasicDto: BreweryBasicDto): BreweryBasic {
    return BreweryBasic(
        id = breweryBasicDto.id,
        name = breweryBasicDto.name,
        breweryType = breweryBasicDto.breweryType,
        country = breweryBasicDto.country.orEmpty()
    )
}