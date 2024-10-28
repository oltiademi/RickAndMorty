package com.example.rickandmorty.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.data.model.Character
import com.example.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.example.rickandmorty.ui.home.CharacterListAdapter
import com.example.rickandmorty.ui.home.HomeViewModel


class EpisodeDetailsFragment : Fragment() {
    val adapter = CharacterListAdapter(this::navigateToCharacter)
    lateinit var binding: FragmentEpisodeDetailsBinding
    val args: EpisodeDetailsFragmentArgs by navArgs()

    val episodesViewModel: EpisodesViewModel by viewModels<EpisodesViewModel>()
    val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val episodeId = args.episodeId
        episodesViewModel.getEpisodeById(episodeId).observe(viewLifecycleOwner) { episode ->
            binding.episodeTitle.text = episode.name
            binding.episodeNumber.informationText.text = "Episode"
            binding.episodeNumber.informationType.text = episode.episodeNumber
            binding.episodeReleaseDate.informationText.text = "Date"
            binding.episodeReleaseDate.informationType.text = episode.airDate

            homeViewModel.charactersList.observe(viewLifecycleOwner) { characters ->
                val characterId = episode.characters.map {
                    it.substringAfterLast("/")
                }
                characters.let {
                    adapter.characters = it.filter { character ->
                        characterId.contains(character.id.toString())
                    }.sortedBy { character -> character.id }
                }
            }
        }
        binding.castRecycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.castRecycler.adapter = adapter

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun navigateToCharacter(character: Character) {
        val action =
            EpisodeDetailsFragmentDirections.actionEpisodeDetailsFragmentToCharacterFragment(
                character.id.toString()
            )
        findNavController().navigate(action)
    }
}