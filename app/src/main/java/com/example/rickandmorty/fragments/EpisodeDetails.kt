package com.example.rickandmorty.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.CharactersAdapter
import com.example.rickandmorty.api.ApiUtils
import com.example.rickandmorty.databinding.FragmentDetailsEpisodeBinding
import com.example.rickandmorty.db.App
import com.example.rickandmorty.model.CertainEpisodeModel
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodeDetails : Fragment(R.layout.fragment_details_episode) {
    private lateinit var binding : FragmentDetailsEpisodeBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    var episodeId : Int = 0
    lateinit var episode : CertainEpisodeModel
    val adapter = CharactersAdapter()
    lateinit var navController : NavController
    var idArray = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episodeId = arguments?.getInt("episode")!!
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsEpisodeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        navController = Navigation.findNavController(view)

        adapter.setOnClickListener {
            val action = EpisodeDetailsDirections.actionEpisodeDetailsToCharacterFragment(it)
            navController.navigate(action)
        }


        coroutineScope.launch {
            episode = withContext(Dispatchers.Default){
                val data = ApiUtils.apiService?.getEpisodeById(episodeId)!!
                data
            }
            binding.titleTextView.text = episode.name
            binding.episodeTextView.text = episode.episode
            binding.airDateTextView.text = episode.airDate
            for (i in episode.characters){
                idArray.add(i.substring(42))
            }

            val charactersArray = withContext(Dispatchers.Default){
                val data = mutableListOf<Character>()
                for(i in idArray){
                    val char = App.instance.db.getCharacterDao().getCharacterById(i.toInt())
                    if(char != null){
                        data.add(char)
                    } else {
                        val character = ApiUtils.apiService?.getCharacterById(i.toInt())!!
                        data.add(character)
                        App.instance.db.getCharacterDao().insert(character)
                    }
                }
                data
            }
            binding.loadingProgress.visible(false)
            adapter.setData(charactersArray)
        }



    }

    fun initRecycler() {
        binding.recyclerviewDetals.apply {
            adapter = this@EpisodeDetails.adapter
            layoutManager = GridLayoutManager(context,2)
        }
    }

}