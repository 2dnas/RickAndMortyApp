package com.example.rickandmorty.DI.app

import com.example.rickandmorty.DI.activity.ActivityComponent
import com.example.rickandmorty.DI.activity.ActivityModule
import dagger.Component


@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponent(activityModule : ActivityModule) : ActivityComponent

}