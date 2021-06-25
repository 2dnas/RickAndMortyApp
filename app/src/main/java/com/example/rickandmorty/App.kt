package com.example.rickandmorty

import android.app.Application
import androidx.room.Room
import com.example.rickandmorty.DI.app.AppModule
import com.example.rickandmorty.DI.app.DaggerAppComponent
import com.example.rickandmorty.db.AppDatabase

class App : Application() {
    lateinit var db : AppDatabase

    public val appComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var instance : App
        private set
    }
    override fun onCreate() {
        super.onCreate()

        instance = this

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).allowMainThreadQueries().build()
    }

}