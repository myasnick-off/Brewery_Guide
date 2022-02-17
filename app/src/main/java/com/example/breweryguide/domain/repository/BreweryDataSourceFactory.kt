package com.example.breweryguide.domain.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.network.ApiHolder
import io.reactivex.rxjava3.disposables.CompositeDisposable


class BreweryDataSourceFactory(
    private val compositeDisposable: CompositeDisposable
)
    : DataSource.Factory<Int, BreweryBasic>() {

    val mutableLiveData = MutableLiveData<BreweryDataSource>()

    override fun create(): DataSource<Int, BreweryBasic> {
        val breweryDataSource =
            BreweryDataSource(RepositoryImpl(ApiHolder.breweryApiService), compositeDisposable)
        mutableLiveData.postValue(breweryDataSource)
        return breweryDataSource
    }
}