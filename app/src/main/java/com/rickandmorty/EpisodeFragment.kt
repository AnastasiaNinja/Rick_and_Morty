package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rickandmorty.databinding.FragmentMainScreenBinding
import com.rickandmorty.model.adapters.EpisodeAdapter
import com.rickandmorty.model.viewmodel.EpisodesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EpisodeFragment : Fragment() {
   lateinit var binding: FragmentMainScreenBinding
    private lateinit var episodeAdapter: EpisodeAdapter
    private val viewModel: EpisodesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        loadingData()



    }

    private fun loadingData() {
        lifecycleScope.launch {
            viewModel.listData.collect{ pagingData->
                episodeAdapter.submitData(pagingData)

            }
        }
    }

    private fun setupRv() {
        episodeAdapter = EpisodeAdapter()
        binding.recyclerView.apply {

            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            adapter = episodeAdapter
            setHasFixedSize(true)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = EpisodeFragment()
    }
}