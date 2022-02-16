package com.example.breweryguide.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breweryguide.domain.repository.Repository
import com.example.breweryguide.domain.repository.RepositoryImpl
import com.example.breweryguide.network.ApiHolder
import com.example.breweryguide.ui.AppState
import com.example.breweryguide.utils.dtoToModelBasicConvertor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ListViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(ApiHolder.breweryApiService)
)
    : ViewModel() {

    fun getLiveData(): LiveData<AppState> = liveData

    fun getBreweryList() {
        liveData.value = AppState.Loading
        repository.getBreweryListFromServer()
            .subscribeOn(Schedulers.io())
            .map { list ->
                list.map { dtoToModelBasicConvertor(it) }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    liveData.value = AppState.ListSuccess(it)
                },
                {
                    liveData.value = AppState.Error(it)
                }
            )
    }
}