package com.example.rickandmorty.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.CharactersResponse
import com.example.rickandmorty.domain.CharactersRepository
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : ViewModel() {
    val charactersList = MutableLiveData<List<Character>>()
    private val repository = CharactersRepository()

    init {
        getCharacters()
    }

    fun getCharacters() {
        val characterList = mutableListOf<Character>()
        for (i in 1..42) {
            repository.service.getCharacters(i)
                .enqueue(object : retrofit2.Callback<CharactersResponse> {
                    override fun onResponse(
                        call: Call<CharactersResponse>,
                        response: Response<CharactersResponse>
                    ) {
                        characterList.addAll(response.body()!!.results)
                        charactersList.postValue(characterList)
                    }

                    override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }
    }
}