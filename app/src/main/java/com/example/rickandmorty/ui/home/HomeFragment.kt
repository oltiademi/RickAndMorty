package com.example.rickandmorty.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var adapter: CharacterListAdapter
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.charactersList.observe(viewLifecycleOwner) { characters ->
            binding.searchField.doOnTextChanged { text, _, _, _ ->
                adapter.characters = emptyList()
                val searchText = text.toString().trim().lowercase()
                adapter.characters = if (searchText.isEmpty()) {
                    characters.sortedBy { it.id }
                } else {
                    characters.filter { character ->
                        character.name!!.lowercase().contains(searchText.lowercase())
                    }.sortedBy { it.id }
                }
            }
            adapter.characters = characters.sortedBy { character -> character.id }
        }
        adapter = CharacterListAdapter(this::navigate)
        binding.characterList.layoutManager = LinearLayoutManager(requireActivity())
        binding.characterList.adapter = adapter
    }

    private fun navigate(character: Character) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToCharacterFragment(character.id.toString())
        findNavController().navigate(action)
    }
}