package com.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rickandmorty.databinding.ActivityMainBinding
import com.rickandmorty.model.adapters.CharacterAdapter
import com.rickandmorty.model.adapters.EpisodeAdapter
import com.rickandmorty.model.adapters.LocationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_RickandMorty)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFrag(PersonFragment.newInstance(), R.id.fragment_container_view)

        setBottomNavigationView()

    }




    private fun openFrag(f: Fragment, idHolder: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, f)
            .commit()
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.bringToFront()
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){

                R.id.item1 -> {
                    openFrag(PersonFragment.newInstance(), R.id.fragment_container_view)
                    return@setOnItemSelectedListener true
                }
                R.id.item2 -> {
                    openFrag(EpisodeFragment.newInstance(), R.id.fragment_container_view)
                    return@setOnItemSelectedListener true
                }
                R.id.item3 -> {
                    openFrag(LocationFragment.newInstance(), R.id.fragment_container_view)
                    return@setOnItemSelectedListener true
                }
            }

            false
        }
        binding.bottomNavigationView.setOnItemReselectedListener {
            println("reselected")
        }
    }
}

