package com.example.rickandmorty.ui.episodes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.data.model.EpisodeResponse
import com.example.rickandmorty.domain.CharactersRepository
import retrofit2.Call
import retrofit2.Response

class EpisodesViewModel : ViewModel() {
    var episodes = MutableLiveData<List<Episode>>()
    private val repository = CharactersRepository()
    val episode = MutableLiveData<Episode>()

    init {
        getEpisodes()
    }

    fun getEpisodes() {
        val episodeList = mutableListOf<Episode>()
        for (i in 1..3) {
            repository.service.getEpisodes(i)
                .enqueue(object : retrofit2.Callback<EpisodeResponse> {
                    override fun onResponse(
                        call: Call<EpisodeResponse>,
                        response: Response<EpisodeResponse>
                    ) {
                        episodeList.addAll(response.body()!!.results)
                        episodes.postValue(episodeList)
                    }

                    override fun onFailure(call: Call<EpisodeResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }
    }

    fun getEpisodeById(id: Int?): MutableLiveData<Episode> {
        repository.service.getEpisodeById(id).enqueue(object : retrofit2.Callback<Episode> {
            override fun onResponse(
                call: Call<Episode>,
                response: Response<Episode>
            ) {
                episode.postValue(response.body())
            }

            override fun onFailure(call: Call<Episode>, t: Throwable) {
                t.printStackTrace()
            }

        })
        return episode
    }
}