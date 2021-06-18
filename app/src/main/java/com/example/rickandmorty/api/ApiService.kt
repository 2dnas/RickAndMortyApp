package com.example.rickandmorty.api

import com.example.rickandmorty.model.CertainEpisodeModel
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.CharacterModel
import com.example.rickandmorty.model.EpisodesModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("episode")
    suspend fun getEpisodes(@Query("page")page : Int) : EpisodesModel

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id")id : Int) : CertainEpisodeModel

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id")id:Int) : Character

}