package com.example.breweryguide.domain.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.network.ApiHolder


class BreweryDataSourceFactory : DataSource.Factory<Int, BreweryBasic>() {

    val mutableLiveData = MutableLiveData<BreweryDataSource>()

    override fun create(): DataSource<Int, BreweryBasic> {
        val breweryDataSource = BreweryDataSource(ApiHolder.breweryApiService)
        mutableLiveData.postValue(breweryDataSource)
        return breweryDataSource
    }
}