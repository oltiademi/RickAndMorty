package com.example.rickandmorty.ui.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.data.model.Episode
import com.example.rickandmorty.databinding.ItemEpisodeListBinding

class CharactersEpisodeListAdapter(val onClick: (episode: Episode) -> Unit) :
    RecyclerView.Adapter<CharactersEpisodeListAdapter.ViewHolder>() {
    var episodes: List<Episode> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: ItemEpisodeListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemEpisodeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = episodes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = episodes[position]
        with(holder.binding) {
            episodeTitle.text = episode.name
            episodeNumber.text = episode.episodeNumber
            episodeReleaseDate.text = episode.airDate
            root.setOnClickListener {
                onClick(episode)
            }
        }
    }
}