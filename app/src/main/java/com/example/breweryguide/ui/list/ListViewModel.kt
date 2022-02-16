package com.example.breweryguide.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breweryguide.domain.repository.Repository
import com.example.breweryguide.domain.repository.RepositoryImpl
import com.example.breweryguide.network.ApiHolder
import com.example.breweryguide.network.dto.BreweryBasicDto
import com.example.breweryguide.ui.AppState
import com.example.breweryguide.utils.dtoToModelBasicConvertor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ListViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(ApiHolder.breweryApiService)
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> = liveData

    fun getBreweryList() {
        liveData.value = AppState.Loading
        repository.getBreweryListFromServer().enqueue(object : Callback<List<BreweryBasicDto>> {

            override fun onResponse(
                call: Call<List<BreweryBasicDto>>,
                response: Response<List<BreweryBasicDto>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val breweryList = response.body()!!.map { dtoToModelBasicConvertor(it) }
                        liveData.value = AppState.ListSuccess(breweryList)
                } else {
                    liveData.value = AppState.Error(IOException())
                }
            }

            override fun onFailure(call: Call<List<BreweryBasicDto>>, t: Throwable) {
                liveData.value = AppState.Error(t)
            }

        })

    }
}