package com.example.breweryguide.domain.repository

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.breweryguide.domain.model.BreweryBasic
import com.example.breweryguide.network.BreweryApiService
import com.example.breweryguide.network.dto.BreweryBasicDto
import com.example.breweryguide.utils.dtoToModelBasicConvertor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BreweryDataSource(private val apiService: BreweryApiService) : PageKeyedDataSource<Int, BreweryBasic>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BreweryBasic>) {
            getBreweries(callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BreweryBasic>) {
            val nextPageNo = params.key + 1
            getMoreBreweries(params.key, nextPageNo, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BreweryBasic>) {
            val previousPageNo = if (params.key > 1) params.key - 1 else 0
            getMoreBreweries(params.key, previousPageNo, callback)
    }

    private fun getBreweries(callback: LoadInitialCallback<Int, BreweryBasic>) {

        apiService.getBreweryList(1).enqueue(object : Callback<List<BreweryBasicDto>> {

            override fun onResponse(call: Call<List<BreweryBasicDto>>, response: Response<List<BreweryBasicDto>>) {
                if (response.isSuccessful) {
                        response.body()?.let {
                        val breweryList = it.map {item -> dtoToModelBasicConvertor(item) }
                        callback.onResult(breweryList, null, 2)
                    }
                } else {
                    Log.e("mylog", "Response unsuccessful!")
                }
            }
            override fun onFailure(call: Call<List<BreweryBasicDto>>, t: Throwable) {
                Log.e("mylog", "${t.message}")
            }
        })
    }

    private fun getMoreBreweries(pageNo: Int, previousOrNextPageNo: Int, callback: LoadCallback<Int, BreweryBasic>) {

        apiService.getBreweryList(pageNo).enqueue(object : Callback<List<BreweryBasicDto>> {

            override fun onResponse(call: Call<List<BreweryBasicDto>>, response: Response<List<BreweryBasicDto>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val breweryList = it.map {item -> dtoToModelBasicConvertor(item) }
                        callback.onResult(breweryList, previousOrNextPageNo)
                    }
                } else {
                    Log.e("mylog", "Response unsuccessful!")
                }
            }

            override fun onFailure(call: Call<List<BreweryBasicDto>>, t: Throwable) {
                Log.e("mylog", "${t.message}")
            }
        })

    }
}