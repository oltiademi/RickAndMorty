package com.example.rickandmorty.ui.location

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
import com.example.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.example.rickandmorty.ui.home.CharacterListAdapter
import com.example.rickandmorty.ui.home.HomeViewModel

class LocationDetailsFragment : Fragment() {
    private lateinit var binding: FragmentLocationDetailsBinding
    private var adapter: CharacterListAdapter = CharacterListAdapter(this::navigate)
    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()
    private val locationViewModel: LocationViewModel by viewModels<LocationViewModel>()

    val args: LocationDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val locationId = args.locationId.toInt()
        locationViewModel.getLocationById(locationId).observe(viewLifecycleOwner) { location ->
            val residentIds = location.residents?.map { resident ->
                resident.substringAfterLast("/")
            }
            homeViewModel.charactersList.observe(viewLifecycleOwner) { characters ->
                characters.let {
                    binding.residentsRecycler.layoutManager = LinearLayoutManager(requireActivity())
                    adapter.characters = it.filter { resident ->
                        residentIds!!.contains(resident.id.toString())
                    }.sortedBy { resident -> resident.id }
                    binding.residentsRecycler.adapter = adapter
                }
            }
            binding.locationName.text = location.name
            binding.type.informationText.text = "Type"
            binding.type.informationType.text = location.type
            binding.dimension.informationText.text = "Dimension"
            binding.dimension.informationType.text = location.dimension
        }
        binding.backButton.setOnClickListener {
            val action =
                LocationDetailsFragmentDirections.actionLocationDetailsFragmentToLocationsFragment()
            findNavController().navigate(action)
        }
    }

    fun navigate(character: Character) {
        val action =
            LocationDetailsFragmentDirections.actionLocationDetailsFragmentToCharacterFragment(
                character.id.toString()
            )
        findNavController().navigate(action)
    }
}