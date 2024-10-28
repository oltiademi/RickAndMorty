package com.example.rickandmorty.ui.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.data.model.Location
import com.example.rickandmorty.databinding.ItemLocationsListBinding

class LocationsListAdapter(val onClick: (Location)->Unit) : RecyclerView.Adapter<LocationsListAdapter.ViewHolder>() {
    var locations: List<Location> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: ItemLocationsListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLocationsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locations[position]
        with(holder.binding) {
            locationName.text = location.name
            locationType.text = location.type
            holder.itemView.setOnClickListener {
                onClick(location)
            }
        }
    }
}