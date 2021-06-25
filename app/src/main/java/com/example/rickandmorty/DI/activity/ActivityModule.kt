package com.example.rickandmorty.DI.activity

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity : AppCompatActivity) {

    @Provides
    fun activity() = activity

    @Provides
    fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager








}
