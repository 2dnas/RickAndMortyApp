package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.api.ApiUtils
import com.example.rickandmorty.model.CertainEpisodeModel
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    suspend fun getEpisodeById(id : Int) : CertainEpisodeModel? {
        return ApiUtils.apiService?.getEpisodeById(id)

    }
}