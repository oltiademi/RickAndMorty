package com.example.rickandmorty.ui.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.domain.CharactersRepository
import retrofit2.Call
import retrofit2.Response

class CharacterViewModel : ViewModel() {
    var character = MutableLiveData<Character?>()
    var episodes = MutableLiveData<List<Episode>>()
    private val repository = CharactersRepository()
    var id: Int? = null

    init {
        getCharacter(null)
    }

    fun getCharacter(id: Int?): MutableLiveData<Character?> {
        repository.service.getCharacter(id).enqueue(object : retrofit2.Callback<Character> {
            override fun onResponse(
                call: Call<Character>,
                response: Response<Character>
            ) {
                character.postValue(response.body())
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                println("err")
            }
        })
        return character
    }


}

