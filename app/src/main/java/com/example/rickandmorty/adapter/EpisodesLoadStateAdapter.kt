package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.LoadingLayoutBinding
import com.example.rickandmorty.visible

class EpisodesLoadStateAdapter(private val retry : () -> Unit)
    : LoadStateAdapter<EpisodesLoadStateAdapter.EpisodeLoadViewHolder>(){


        inner class EpisodeLoadViewHolder(
    private val binding : LoadingLayoutBinding,
    private val retry : () -> Unit) : RecyclerView.ViewHolder(binding.root){
        fun bind(loadState : LoadState) {
            if(loadState is LoadState.Error){
                binding.textViewError.text = loadState.error.localizedMessage
            }
            binding.progressbar.visible(loadState is LoadState.Loading)
            binding.buttonRetry.visible(loadState is LoadState.Error)
            binding.textViewError.visible(loadState is LoadState.Error)
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: EpisodeLoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): EpisodeLoadViewHolder {
        return EpisodeLoadViewHolder(LoadingLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false),retry)
    }
}