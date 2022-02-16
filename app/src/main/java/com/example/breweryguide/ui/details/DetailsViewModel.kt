package com.example.breweryguide.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breweryguide.domain.repository.Repository
import com.example.breweryguide.domain.repository.RepositoryImpl
import com.example.breweryguide.network.ApiHolder
import com.example.breweryguide.network.dto.BreweryDetailsDto
import com.example.breweryguide.ui.AppState
import com.example.breweryguide.utils.dtoToModelBasicConvertor
import com.example.breweryguide.utils.dtoToModelDetailsConvertor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.NullPointerException

class DetailsViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(ApiHolder.breweryApiService)
) : ViewModel() {

    fun getLiveData(): LiveData<AppState> = liveData

    fun getBreweryDetails(breweryId: String) {
        liveData.value = AppState.Loading
        repository.getBreweryFromServer(breweryId).enqueue(object : Callback<BreweryDetailsDto> {

            override fun onResponse(
                call: Call<BreweryDetailsDto>,
                response: Response<BreweryDetailsDto>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val brewery = dtoToModelDetailsConvertor(response.body()!!)
                    liveData.value = AppState.DetailsSuccess(brewery)
                } else {
                    liveData.value = AppState.Error(IOException())
                }
            }

            override fun onFailure(call: Call<BreweryDetailsDto>, t: Throwable) {
                liveData.value = AppState.Error(t)
            }
        })

    }
}