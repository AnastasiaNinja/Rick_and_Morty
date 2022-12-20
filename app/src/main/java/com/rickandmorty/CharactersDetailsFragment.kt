package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.rickandmorty.databinding.FragmentCharactersDetailsBinding
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.ResultsCharacters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CharactersDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCharactersDetailsBinding


    @Inject
    lateinit var apiService: ApiService

    private val id: Long
    get() = requireArguments().getLong(ARG_CHARACTER_ID)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersDetailsBinding.inflate(inflater, container, false)
        loadData()
        return binding.root
    }

    private fun loadData() {
        lifecycleScope.launch {
            val responseDetails = apiService.getCharacterDetails(id)
            bindCharacterDetails(responseDetails.body()!!)
        }
    }

    private fun bindCharacterDetails(details: ResultsCharacters) {
        binding.species.text = details.species
    }

    companion object {

        const val ARG_CHARACTER_ID = "ARG_CHARACTER_ID"
        @JvmStatic
        fun newInstance(characterId: Long): CharactersDetailsFragment {
            val fragment = CharactersDetailsFragment()
            fragment.arguments = bundleOf(ARG_CHARACTER_ID to characterId)
            return fragment
        }
    }
}