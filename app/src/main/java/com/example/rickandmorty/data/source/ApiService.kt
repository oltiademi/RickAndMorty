package com.example.rickandmorty.data.source

import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.CharactersResponse
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.data.model.EpisodeResponse
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.data.model.LocationsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/character/")
    fun getCharacters(@Query("page") page: Int?): Call<CharactersResponse>

    @GET("/api/character/{id}")
    fun getCharacter(@Path("id") id: Int?): Call<Character>

    @GET("/api/episode/")
    fun getEpisodes(@Query("page") page: Int?): Call<EpisodeResponse>

    @GET("/api/location/")
    fun getLocations(@Query("page") page: Int?): Call<LocationsResponse>

    @GET("/api/location/{id}")
    fun getLocationById(@Path("id") id: Int?): Call<Location>

    @GET("/api/episode/{id}")
    fun getEpisodeById(@Path("id") id: Int?): Call<Episode>
}