package com.example.rickandmorty

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty.db.App
import com.example.rickandmorty.fragments.EpisodesFragment
import com.example.rickandmorty.fragments.EpisodesFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    lateinit var navController: NavController
    lateinit var arrayAdapter : ArrayAdapter<String>
    lateinit var backButton : ImageView
    lateinit var namesArray : MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController  = navHostFragment.navController

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        namesArray = mutableListOf<String>()
        App.instance.db.getCharacterDao().getAllCharacter().forEach { namesArray.add(it.name!!) }
        App.instance.db.getEpisodeDao().getEpisodes()?.forEach { namesArray.add(it.name!!) }
        arrayAdapter = ArrayAdapter(this,R.layout.search_item,namesArray)
        val parent = supportActionBar!!.customView.parent as Toolbar
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _ , destination , _ ->
            when(destination.id) {
                R.id.episodesFragment -> return@addOnDestinationChangedListener
                else -> parent.setNavigationIcon(R.drawable.ic_baseline_arrow_back)
            }

        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val logo = findViewById<ImageView>(R.id.imageView)
        val search = findViewById<AutoCompleteTextView>(R.id.searchview)
        search.setAdapter(arrayAdapter)

        when(item.itemId){
            R.id.app_bar_menu_search -> {
                Toast.makeText(this,"vava",Toast.LENGTH_LONG).show()
                logo.visible(false)
                search.visible(true)
                search.setOnDismissListener {
                    if(search.text.toString() == ""){
                        return@setOnDismissListener
                    } else if(!namesArray.contains(search.text.toString())){
                        return@setOnDismissListener
                    }
                    navController.navigate(EpisodesFragmentDirections.actionEpisodesFragmentToCharacterFragment(search.text.toString()))
                    search.visible(false)
                    logo.visible(true)
                }

            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }




}