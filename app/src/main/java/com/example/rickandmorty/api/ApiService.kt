package com.example.rickandmorty.api

import com.example.rickandmorty.model.CertainEpisodeModel
import com.example.rickandmorty.model.Character
import com.example.rickandmorty.model.CharacterModel
import com.example.rickandmorty.model.EpisodesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("episode")
    suspend fun getEpisodes(@Query("page")page : Int) : EpisodesModel

    @GET("episode/{id}")
    fun getEpisodeById(@Path("id")id : Int) : Call<CertainEpisodeModel>

    @GET("character/{id}")
    fun getCharacterById(@Path("id")id:Int) : Call<Character>

    @GET("character/{ids}")
    fun getAllCharacterById(@Path("ids") ids : List<Int>) : Call<List<Character>>

    @GET("character/")
    fun getCharacterByName(@Query("name")name :String) : Call<CharacterModel>
}