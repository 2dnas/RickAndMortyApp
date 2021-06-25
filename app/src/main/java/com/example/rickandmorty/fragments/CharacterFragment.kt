package com.example.rickandmorty.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.EpisodeAdapterForCharacters
import com.example.rickandmorty.databinding.FragmentCharacterBinding
import com.example.rickandmorty.App
import com.example.rickandmorty.model.CertainEpisodeModel
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.setImage
import com.example.rickandmorty.viewmodel.CharactersViewModel
import com.example.rickandmorty.viewmodel.EpisodeViewModelCharacters
import com.example.rickandmorty.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterFragment : BaseFragment(R.layout.fragment_character) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    private lateinit var binding: FragmentCharacterBinding
    var character = ""
    var adapter = EpisodeAdapterForCharacters()
    lateinit var navController: NavController
    @Inject lateinit var episodeViewModelCharacters : EpisodeViewModelCharacters
    @Inject lateinit var charactersViewModel : CharactersViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        character = arguments?.getString("character", "Rick Sanchez").toString()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()



        navController = Navigation.findNavController(view)

        coroutineScope.launch {
            val id = App.instance.db.getCharacterDao().getCharacterByName(character)?.id
            var char : Character
                charactersViewModel?.getCharacterByName(character)?.observe(viewLifecycleOwner,{
                    char = it.results[0]
                    binding.image.setImage(char.image!!)
                    binding.name.text = char.name
                    binding.status.text = char.status
                    val charInEpisodesIds = mutableListOf<String>()
                    val episodesList = mutableListOf<CertainEpisodeModel>()

                    char.episode?.forEach { charInEpisodesIds.add(it.substring(40)) }

                    for (episode in charInEpisodesIds) {
                        episodeViewModelCharacters?.getEpisodeById(episode.toInt())?.observe(viewLifecycleOwner, { model ->
                            episodesList.add(model)
                            binding.loadingProgress.visible(false)
                                adapter.setData(episodesList)

                        })
                    }
                })







        }

        adapter.setOnClickListener {
            val action = CharacterFragmentDirections.actionCharacterFragmentToEpisodeDetails(it)
            navController.navigate(action)
        }
    }

    fun initRecyclerView() {
        binding.recyclerviewCharacter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CharacterFragment.adapter
        }
    }
}