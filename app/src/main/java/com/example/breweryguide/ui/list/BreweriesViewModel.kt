package com.example.breweryguide.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.domain.repository.BreweryDataSource
import com.example.breweryguide.domain.repository.BreweryDataSourceFactory
import com.example.breweryguide.ui.AppState
import com.example.breweryguide.utils.PAGE_SIZE
import io.reactivex.rxjava3.disposables.CompositeDisposable

class BreweriesViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val breweryDataSourceFactory = BreweryDataSourceFactory(compositeDisposable)

    private val  breweryListLiveData : LiveData<PagedList<BreweryBasic>> by lazy {
        createBreweryListLiveData()
    }
    private val  appStateLiveData : LiveData<AppState> by lazy {
        Transformations
            .switchMap(breweryDataSourceFactory.mutableLiveData, BreweryDataSource::appStateLiveData)
    }

    fun getPagedListData() = breweryListLiveData

    fun getAppStateData() = appStateLiveData

    private fun createBreweryListLiveData(): LiveData<PagedList<BreweryBasic>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        return LivePagedListBuilder(breweryDataSourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}