package com.example.breweryguide.domain.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.ui.AppState
import com.example.breweryguide.utils.FIRST_PAGE
import com.example.breweryguide.utils.dtoToModelBasicConvertor
import io.reactivex.rxjava3.disposables.CompositeDisposable

class BreweryDataSource(
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
    )
    : PageKeyedDataSource<Int, BreweryBasic>() {

    // LiveData для отслеживания состояний загрузки данных
    val appStateLiveData: MutableLiveData<AppState> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BreweryBasic>) {
        appStateLiveData.postValue(AppState.Loading)
        compositeDisposable.add(repository.getBreweryListFromServer(FIRST_PAGE)
            .subscribe(
                {
                    val breweryList = it.map {item -> dtoToModelBasicConvertor(item) }
                    callback.onResult(breweryList, null, FIRST_PAGE + 1)
                    appStateLiveData.postValue(AppState.ListSuccess)
                },
                {
                    Log.e("mylog", "${it.message}")
                    appStateLiveData.postValue(AppState.Error(it))
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BreweryBasic>) {
        appStateLiveData.postValue(AppState.Loading)
        compositeDisposable.add(repository.getBreweryListFromServer(params.key)
            .subscribe(
                {
                    val breweryList = it.map {item -> dtoToModelBasicConvertor(item) }
                    callback.onResult(breweryList, params.key + 1)
                    appStateLiveData.postValue(AppState.ListSuccess)
                },
                {
                    Log.e("mylog", "${it.message}")
                    appStateLiveData.postValue(AppState.Error(it))
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BreweryBasic>) {}
}