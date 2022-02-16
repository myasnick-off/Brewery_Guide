package com.example.breweryguide.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.domain.repository.BreweryDataSource
import com.example.breweryguide.domain.repository.BreweryDataSourceFactory

class BreweriesViewModel : ViewModel() {

    private var breweryList : LiveData<PagedList<BreweryBasic>> = MutableLiveData()
    private var mutableLiveData = MutableLiveData<BreweryDataSource>()

    init {
        val factory : BreweryDataSourceFactory by lazy {
            BreweryDataSourceFactory()
        }
        mutableLiveData = factory.mutableLiveData

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        breweryList = LivePagedListBuilder(factory, config).build()
    }

    fun getData() : LiveData<PagedList<BreweryBasic>>{
        return breweryList
    }
}