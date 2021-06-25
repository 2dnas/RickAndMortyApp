package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.EpisodesItemListBinding
import com.example.rickandmorty.model.CertainEpisodeModel
import com.example.rickandmorty.model.Episode

class EpisodeAdapterForCharacters : RecyclerView.Adapter<EpisodeAdapterForCharacters.EpisodeViewHolder>() {
    var dataset = listOf<CertainEpisodeModel>()


    inner class EpisodeViewHolder(private val binding : EpisodesItemListBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun bindEpisode(episode : CertainEpisodeModel) = with(binding) {
            binding.root.setOnClickListener {
                listener?.invoke(episode.id ?: 0)
            }
            titleTextView.text = episode.name
            episodeTextView.text = episode.episode
            airDateTextView.text = episode.airDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(EpisodesItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = dataset[position]
        holder.bindEpisode(item)
    }

    override fun getItemCount() = dataset.size

    fun setData(list : List<CertainEpisodeModel>) {
        dataset = list
        notifyDataSetChanged()
    }

    private var listener : ((id : Int) -> Unit)? = null

    fun setOnClickListener(listener : ((id: Int) -> Unit)) {
        this.listener = listener
    }


}