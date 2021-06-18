package com.example.rickandmorty.api

object ApiUtils {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    val apiService : ApiService?
    get() = RetrofitClient.getClient(BASE_URL)?.create(ApiService::class.java)
}