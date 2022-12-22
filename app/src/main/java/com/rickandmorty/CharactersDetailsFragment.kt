package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.rickandmorty.databinding.FragmentCharactersDetailsBinding
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.ResultsCharacters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.Serializable
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
            handleCharacterDetails(responseDetails.body()!!)
        }
    }

    private fun handleCharacterDetails(details: ResultsCharacters) {
        binding.species.text = details.species
        binding.name.text = details.name
        binding.status.text = details.status
        binding.gender.text = details.gender
        binding.type.text = details.type
        binding.created.text = details.created

        if(details.image!!.isNotBlank()) {
            Glide.with(binding.imageView2.context)
                .load(details.image)
                .placeholder(R.drawable.personicon)
                .error(binding.imageView2)
                .into(binding.imageView2)
        } else {
            binding.imageView2.setImageResource(R.drawable.personicon)
        }

        val args = Bundle()
        args.putStringArrayList("ids", details.episode)

        val fragment = EpisodeFragment()
        fragment.arguments = args
        parentFragmentManager.beginTransaction()
            .add(binding.containerEpisodes.id, fragment).commit()
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