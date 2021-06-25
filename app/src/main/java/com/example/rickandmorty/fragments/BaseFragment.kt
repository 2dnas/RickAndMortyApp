package com.example.rickandmorty.fragments

import androidx.fragment.app.Fragment
import com.example.rickandmorty.BaseActivity
import kotlin.reflect.KProperty

open class BaseFragment(fragment: Int) : Fragment(fragment) {

    private val presentationComponent by lazy {
        (requireContext() as BaseActivity).activityComponent.newPresentationComponent()
    }

    protected val injector get() = presentationComponent
}


