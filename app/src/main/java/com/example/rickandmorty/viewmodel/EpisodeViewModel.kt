package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.rickandmorty.api.ApiService
import com.example.rickandmorty.paging.EpisodesDataSource
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(private val api : ApiService) : ViewModel() {
    val episodes = Pager(PagingConfig(pageSize = 10)){
        EpisodesDataSource(api)
    }.flow.cachedIn(viewModelScope)
}