package com.example.rickandmorty.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.databinding.FragmentEpisodesBinding

class EpisodesFragment : Fragment() {
    var adapter: EpisodesListAdapter = EpisodesListAdapter(this::navigateToEpisodeDetails)
    lateinit var binding: FragmentEpisodesBinding
    private val viewModel: EpisodesViewModel by viewModels<EpisodesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.episodes.observe(viewLifecycleOwner) { episodes ->
            if (episodes != null) {
                binding.searchField.doOnTextChanged { text, _, _, _ ->
                    adapter.episodes = emptyList()
                    val searchText = text.toString().trim().lowercase()
                    adapter.episodes = if (searchText.isEmpty()) {
                        episodes.sortedBy { it.id }
                    } else {
                        episodes.filter { episode ->
                            episode.episodeNumber.lowercase()
                                .contains(searchText) || episode.name.lowercase()
                                .contains(searchText)
                        }.sortedBy { it.id }
                    }
                }
                adapter.episodes = episodes.sortedBy { it.id }
            }
            binding.episodesRecycler.layoutManager = LinearLayoutManager(requireActivity())
            binding.episodesRecycler.adapter = adapter
        }
    }


    private fun navigateToEpisodeDetails(episode: Episode) {
        val action =
            EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeDetailsFragment(episode.id)
        findNavController().navigate(action)
    }
}