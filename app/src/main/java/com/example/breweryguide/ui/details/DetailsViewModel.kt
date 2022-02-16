package com.example.breweryguide.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breweryguide.domain.repository.Repository
import com.example.breweryguide.domain.repository.RepositoryImpl
import com.example.breweryguide.network.ApiHolder
import com.example.breweryguide.ui.AppState
import com.example.breweryguide.utils.dtoToModelDetailsConvertor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailsViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(ApiHolder.breweryApiService)
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> = liveData

    fun getBreweryDetails(breweryId: String) {
        liveData.value = AppState.Loading
        repository.getBreweryFromServer(breweryId)
            .subscribeOn(Schedulers.io())
            .map {
                dtoToModelDetailsConvertor(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    liveData.value = AppState.DetailsSuccess(it)
                },
                {
                    liveData.value = AppState.Error(it)
                }
            )
    }
}