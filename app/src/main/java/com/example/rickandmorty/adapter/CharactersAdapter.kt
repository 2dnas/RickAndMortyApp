package com.example.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.databinding.ListItemCharacterBinding
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.setImage

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {
    var dataset = listOf<Character>()

    inner class CharactersViewHolder(private val binding : ListItemCharacterBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : Character) = with(binding){
            binding.root.setOnClickListener {
                listener!!.invoke(item.name!!)
            }
            characterImage.setImage(item.image!!)
            name.text = item.name
            status.text = item.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(ListItemCharacterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = dataset[position]
        holder.bind(item)
    }

    override fun getItemCount() = dataset.size

    fun setData(list : List<Character>) {
        dataset = list
        notifyDataSetChanged()
    }

    var listener : ((name : String) -> Unit)? =null

    fun setOnClickListener(listener : ((name : String) -> Unit)) {
        this.listener = listener
    }
}