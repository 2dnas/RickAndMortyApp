package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.api.ApiService
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val api : ApiService) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EpisodeViewModel(api) as T
    }
}