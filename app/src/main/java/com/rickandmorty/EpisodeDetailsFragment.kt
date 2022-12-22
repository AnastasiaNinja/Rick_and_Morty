package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.ResultsEpisode
import com.rickandmorty.model.episodes.entities.Episode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EpisodeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentEpisodeDetailsBinding



    @Inject
    lateinit var apiService: ApiService

    private val id: Long
        get() = requireArguments().getLong(ARG_EPISODE_ID)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        requireActivity().theme.applyStyle(R.style.Theme_RickandMorty_ListScreen, true)
        binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)

        loadData()
        return binding.root
    }

    private fun loadData() {
        lifecycleScope.launch {
            val responseDetails = apiService.getEpisodeDetails(id)
            bindEpisodeDetails(responseDetails.body()!!)
        }
    }

    private fun bindEpisodeDetails(details: ResultsEpisode) {
    binding.name.text = details.name
    binding.episodenumber.text = details.episode
    binding.airDate.text = details.airDate

        val args = Bundle()
        args.putStringArrayList("ids", details.characters)

        val fragment = PersonFragment()
        fragment.arguments = args
        parentFragmentManager.beginTransaction()
            .add(binding.containerPersons.id, fragment).commit()
    }

    companion object {

        const val ARG_EPISODE_ID = "ARG_EPISODE_ID"

        @JvmStatic
        fun newInstance(episode: Episode): EpisodeDetailsFragment {
            val fragment = EpisodeDetailsFragment()
            fragment.arguments = bundleOf(ARG_EPISODE_ID to episode)
            return fragment
        }
    }
}