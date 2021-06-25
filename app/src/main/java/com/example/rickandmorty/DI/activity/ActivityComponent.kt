package com.example.rickandmorty.DI.activity

import com.example.rickandmorty.DI.presentation.PresentationComponent
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun newPresentationComponent() : PresentationComponent
}