package com.example.rickandmorty.ui.character

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
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.databinding.FragmentCharacterBinding
import com.example.rickandmorty.ui.episodes.EpisodesViewModel
import com.squareup.picasso.Picasso

class CharacterFragment : Fragment() {
    private val adapter: CharactersEpisodeListAdapter =
        CharactersEpisodeListAdapter(this::navigateToEpisodeDetails)
    private lateinit var binding: FragmentCharacterBinding
    private val viewModel: CharacterViewModel by viewModels<CharacterViewModel>()
    private val episodeViewModel: EpisodesViewModel by viewModels<EpisodesViewModel>()
    private val args: CharacterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val characterId = args.characterId.toInt()
        viewModel.getCharacter(characterId).observe(viewLifecycleOwner) { character ->
            //set the values
            setValues(character!!)
            val episodeIds = character.episode.map { episodeUrl ->
                episodeUrl.substringAfterLast("/")
            }
            episodeViewModel.episodes.observe(viewLifecycleOwner) { episodes ->
                episodes?.let {
                    adapter.episodes = it.filter { episode ->
                        episodeIds.contains(episode.id.toString())
                    }.sortedBy { episode ->
                        episodeIds.indexOf(episode.id.toString())
                    }
                    binding.episodesRecycler.layoutManager =
                        LinearLayoutManager(requireActivity())
                    binding.episodesRecycler.adapter = adapter
                }
            }
            binding.backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setValues(character: Character) {
        //text
        binding.gender.informationText.text = "Gender"
        binding.status.informationText.text = "Status"
        binding.specie.informationText.text = "Specie"
        binding.origin.informationText.text = "Origin"
        binding.type.informationText.text = "Type"
        binding.location.informationText.text = "Location"

        //values
        Picasso.get().load(character.image).transform(CircularTransformation())
            .into(binding.characterImage)
        binding.characterName.text = character.name
        binding.gender.informationType.text = character.gender
        binding.status.informationType.text = character.status
        binding.specie.informationType.text = character.species
        binding.origin.informationType.text = character.origin?.name
        binding.type.informationType.text = character.type
        binding.location.informationType.text = character.location?.name
    }

    private fun navigateToEpisodeDetails(episode: Episode) {
        val action =
            CharacterFragmentDirections.actionCharacterFragmentToEpisodeDetailsFragment(episode.id)
        findNavController().navigate(action)
    }
}