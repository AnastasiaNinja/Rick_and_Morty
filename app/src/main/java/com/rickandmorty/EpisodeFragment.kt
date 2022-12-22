package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rickandmorty.databinding.ActivityMainBinding
import com.rickandmorty.databinding.FragmentMainScreenBinding
import com.rickandmorty.model.adapters.EpisodeAdapter
import com.rickandmorty.model.viewmodel.EpisodesPagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EpisodeFragment : Fragment() {
   lateinit var binding: FragmentMainScreenBinding
    private lateinit var episodeAdapter: EpisodeAdapter
    private val viewModel: EpisodesPagingViewModel by viewModels()


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
        loadData()



    }

    private fun loadData() {
        val hasArgs = arguments != null
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            if (hasArgs) {
                val ids = requireArguments().getStringArrayList("ids")
                viewModel.loadFixedData(ids!!).collect { pagingData->
                    episodeAdapter.submitData(pagingData)
                    if (binding.swipeRefreshLayout.isRefreshing) {
                        binding.swipeRefreshLayout.isRefreshing = false

                    }
                    binding.progressBar.visibility = View.INVISIBLE
                }
            } else {
                viewModel.listData.collect{ pagingData->
                    episodeAdapter.submitData(pagingData)
                    if (binding.swipeRefreshLayout.isRefreshing) {
                        binding.swipeRefreshLayout.isRefreshing = false

                    }
                    binding.progressBar.visibility = View.INVISIBLE
                }
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