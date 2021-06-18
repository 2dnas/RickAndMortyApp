package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.EpisodesItemListBinding
import com.example.rickandmorty.model.Episode


class EpisodesAdapter
    : PagingDataAdapter<Episode,EpisodesAdapter.EpisodeViewHolder>(EpisodesComparator){

    inner class EpisodeViewHolder(private val binding : EpisodesItemListBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bindEpisode(episode : Episode) = with(binding) {
                root.setOnClickListener { listener?.invoke(episode.id!!) }
                titleTextView.text = episode.name
                episodeTextView.text = episode.episode
                airDateTextView.text = episode.airDate
            }
    }

    object EpisodesComparator :DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindEpisode(item) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(EpisodesItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    var listener : ((id : Int) -> Unit)? = null

    fun setOnClickListener(listener : (id : Int) -> Unit) {
        this.listener = listener
    }
}