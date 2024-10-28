package com.example.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
//    @SerializedName("info") var info: Info? = Info(),
    @SerializedName("results") var results: ArrayList<Character> = arrayListOf()
)

data class LocationsResponse(
//    @SerializedName("info") var info: Info? = Info(),
    @SerializedName("results") var results: ArrayList<Location> = arrayListOf()
)

data class EpisodeResponse(
//    @SerializedName("info") var info: Info? = Info(),
    @SerializedName("results") var results: ArrayList<Episode> = arrayListOf()
)

data class Info(
    @SerializedName("count") var count: Int? = null,
    @SerializedName("pages") var pages: Int? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("prev") var prev: String? = null
)

data class Origin(
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null
)

data class Location(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("dimension") var dimension: String? = null,
    @SerializedName("residents") val residents: List<String>? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("created") val created: String? = null,
)

data class Episode(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episodeNumber: String,
    @SerializedName("characters") val characters: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
)

data class Character(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("species") var species: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("origin") var origin: Origin? = Origin(),
    @SerializedName("location") var location: Location? = Location(),
    @SerializedName("image") var image: String? = null,
    @SerializedName("episode") var episode: ArrayList<String> = arrayListOf(),
    @SerializedName("url") var url: String? = null,
    @SerializedName("created") var created: String? = null
)