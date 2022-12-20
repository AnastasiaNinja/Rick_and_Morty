package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.episodes.entities.Episode
import javax.inject.Inject


class EpisodeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentEpisodeDetailsBinding



    private val episode: Episode
      get() = requireArguments().getParcelable<Episode>(ARG_EPISODE) as Episode


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        const val ARG_EPISODE = "ARG_EPISODE"

        @JvmStatic
        fun newInstance(episode: Episode): EpisodeDetailsFragment {
            val fragment = EpisodeDetailsFragment()
            fragment.arguments = bundleOf(ARG_EPISODE to episode)
            return fragment
        }
    }
}