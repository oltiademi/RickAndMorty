package com.example.rickandmorty.ui.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.data.model.LocationsResponse
import com.example.rickandmorty.domain.CharactersRepository
import retrofit2.Call
import retrofit2.Response

class LocationViewModel : ViewModel() {
    var locations = MutableLiveData<List<Location>>()
    var location = MutableLiveData<Location>()
    val repository = CharactersRepository()

    init {
        getLocations()
        getLocationById(null)
    }


    fun getLocations() {
        var locationsList = mutableListOf<Location>()
        for (i in 1..7) {
            repository.service.getLocations(i)
                .enqueue(object : retrofit2.Callback<LocationsResponse> {
                    override fun onResponse(
                        call: Call<LocationsResponse>,
                        response: Response<LocationsResponse>
                    ) {
                        locationsList.addAll(response.body()!!.results)
                        locations.postValue(locationsList)
                    }

                    override fun onFailure(call: Call<LocationsResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                })

        }
    }

    fun getLocationById(id: Int?): MutableLiveData<Location> {
        repository.service.getLocationById(id).enqueue(object : retrofit2.Callback<Location> {
            override fun onResponse(
                call: Call<Location>,
                response: Response<Location>
            ) {
                location.postValue(response.body())

            }

            override fun onFailure(call: Call<Location>, t: Throwable) {
                t.printStackTrace()
            }
        })
        return location
    }
}