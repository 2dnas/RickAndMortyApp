package com.example.rickandmorty.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.EpisodesAdapter
import com.example.rickandmorty.adapter.EpisodesLoadStateAdapter
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.rickandmorty.viewmodel.EpisodeViewModel
import com.example.rickandmorty.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesFragment : BaseFragment(R.layout.fragment_episodes){
    private lateinit var binding : FragmentEpisodesBinding
    @Inject lateinit var episodeViewModel : EpisodeViewModel
    private val adapter = EpisodesAdapter()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
//        val factory = ViewModelFactory(ApiUtils.apiService!!)
        navController = Navigation.findNavController(view)

        adapter.setOnClickListener {
            val action = EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeDetails(it)
            navController.navigate(action)
        }

//        episodeViewModel = ViewModelProvider(this,factory).get(EpisodeViewModel::class.java)

        lifecycleScope.launch {
            episodeViewModel?.episodes?.collectLatest {
                adapter.submitData(it)
            }
        }

    }

    private fun initRecyclerView(){
        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@EpisodesFragment.adapter.withLoadStateFooter(
                footer = EpisodesLoadStateAdapter{this@EpisodesFragment.adapter.retry()}
            )

        }
    }




}