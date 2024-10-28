package com.example.rickandmorty.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.databinding.FragmentLocationsBinding

class LocationsFragment : Fragment() {
    private lateinit var binding: FragmentLocationsBinding
    private var adapter: LocationsListAdapter =
        LocationsListAdapter(this::navigateToLocationDetails)
    private val viewModel: LocationViewModel by viewModels<LocationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.locations.observe(viewLifecycleOwner) { locations ->
            binding.searchField.doOnTextChanged { text, _, _, _ ->
                adapter.locations = emptyList()
                val searchText = text.toString().trim().lowercase()
                adapter.locations = if (searchText.isEmpty()) {
                    locations.sortedBy { it.id }
                } else {
                    locations.filter { location ->
                        location.name?.lowercase()!!.contains(searchText.lowercase())
                    }.sortedBy { it.id }
                }
            }
            adapter.locations = locations.sortedBy { it.id }

            binding.locationsRecycler.layoutManager = LinearLayoutManager(requireActivity())
            binding.locationsRecycler.adapter = adapter
        }
    }

    private fun navigateToLocationDetails(location: Location) {
        val action =
            LocationsFragmentDirections.actionLocationsFragmentToLocationDetailsFragment(location.id.toString())
        findNavController().navigate(action)
    }
}