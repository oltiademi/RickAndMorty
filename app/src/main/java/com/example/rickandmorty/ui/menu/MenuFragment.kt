package com.example.rickandmorty.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    lateinit var binding: FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.menuCharacters.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToHomeFragment()
            findNavController().navigate(action)
        }
        binding.menuLocations.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToLocationsFragment()
            findNavController().navigate(action)
        }
        binding.menuEpisodes.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToEpisodesFragment()
            findNavController().navigate(action)
        }
    }
}