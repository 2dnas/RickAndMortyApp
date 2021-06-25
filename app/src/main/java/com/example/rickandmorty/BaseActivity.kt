package com.example.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmorty.DI.activity.ActivityModule

open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as App).appComponent

    val activityComponent by lazy {
        appComponent.newActivityComponent(ActivityModule(this))
    }

    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent()
    }

    protected val injector get() = presentationComponent
}