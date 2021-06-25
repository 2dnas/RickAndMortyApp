package com.example.rickandmorty.DI.app

import android.app.Application
import com.example.rickandmorty.Constants
import com.example.rickandmorty.api.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(val application : Application) {

    @Provides
    fun application() = application

    @AppScope
    @Provides
    fun apiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)


    @AppScope
    @Provides
    fun retrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }


    fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}