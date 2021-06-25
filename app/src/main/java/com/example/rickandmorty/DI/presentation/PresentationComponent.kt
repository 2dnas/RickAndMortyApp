package com.example.rickandmorty.DI.presentation

import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.fragments.CharacterFragment
import com.example.rickandmorty.fragments.EpisodeDetails
import com.example.rickandmorty.fragments.EpisodesFragment
import dagger.Component
import dagger.Subcomponent

@PresentationScope
@Subcomponent
interface PresentationComponent {
    fun inject(fragment : CharacterFragment)
    fun inject(fragment : EpisodeDetails)
    fun inject(fragment : EpisodesFragment)
    fun inject(activity : MainActivity)
}