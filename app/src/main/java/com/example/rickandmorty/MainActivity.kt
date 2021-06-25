package com.example.rickandmorty

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.rickandmorty.fragments.CharacterFragmentDirections
import com.example.rickandmorty.fragments.EpisodeDetailsDirections
import com.example.rickandmorty.fragments.EpisodesFragmentDirections
import com.example.rickandmorty.viewmodel.CharactersViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity() {
    val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    lateinit var navController: NavController
    lateinit var arrayAdapter : ArrayAdapter<String>
    lateinit var namesArray : MutableList<String>
    lateinit var whichFragmentIAm : String

    @Inject lateinit var charactersViewModel : CharactersViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController  = navHostFragment.navController

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        namesArray = mutableListOf()
        arrayAdapter = ArrayAdapter(this,R.layout.search_item,namesArray)

        if(isNetworkAvailable()){
            charactersViewModel?.getAllCharacter()?.observe(this,{ character ->
                character?.forEach { namesArray.add(it.name!!) }
                arrayAdapter = ArrayAdapter(this,R.layout.search_item,namesArray)

            })
        } else {
            App.instance.db.getCharacterDao().getAllCharacter().forEach { namesArray.add(it.name!!) }
            App.instance.db.getEpisodeDao().getEpisodes()?.forEach { namesArray.add(it.name!!) }
            arrayAdapter = ArrayAdapter(this,R.layout.search_item,namesArray)

        }
        App.instance.db.getCharacterDao().getAllCharacter().forEach { namesArray.add(it.name!!) }
        App.instance.db.getEpisodeDao().getEpisodes()?.forEach { namesArray.add(it.name!!) }


        val parent = supportActionBar!!.customView.parent as Toolbar
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _ , destination , _ ->
            when(destination.id) {
                R.id.episodesFragment -> {
                    whichFragmentIAm = "episodesFragment"
                    return@addOnDestinationChangedListener
                }
                R.id.characterFragment -> {
                    whichFragmentIAm = "characterFragment"
                    parent.setNavigationIcon(R.drawable.ic_baseline_arrow_back)
                }
                R.id.episodeDetails -> {
                    whichFragmentIAm = "episodeDetails"
                    parent.setNavigationIcon(R.drawable.ic_baseline_arrow_back)
                }

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

                    when(whichFragmentIAm){
                        "episodesFragment" -> {
                            navController.navigate(EpisodesFragmentDirections.actionEpisodesFragmentToCharacterFragment(search.text.toString()))
                        }
                        "characterFragment" -> {
                            navController.navigate(CharacterFragmentDirections.actionCharacterFragmentSelf(search.text.toString()))
                        }
                        "episodeDetails" -> {
                            navController.navigate(EpisodeDetailsDirections.actionEpisodeDetailsToCharacterFragment(search.text.toString()))
                        }
                    }

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

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }




}