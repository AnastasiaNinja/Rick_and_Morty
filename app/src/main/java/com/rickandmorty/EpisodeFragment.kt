package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rickandmorty.databinding.ActivityMainBinding
import com.rickandmorty.databinding.FragmentMainEpisodeBinding
import com.rickandmorty.databinding.FragmentMainScreenBinding
import com.rickandmorty.model.adapters.EpisodeAdapter
import com.rickandmorty.model.api.CharacterFilterParams
import com.rickandmorty.model.api.EpisodeFilterParams
import com.rickandmorty.model.viewmodel.EpisodesPagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EpisodeFragment : Fragment() {
   lateinit var binding: FragmentMainEpisodeBinding
    private lateinit var episodeAdapter: EpisodeAdapter
    private val viewModel: EpisodesPagingViewModel by viewModels()
    private var noDataAlreadyShown = false
    private var curFilter = EpisodeFilterParams()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainEpisodeBinding.inflate(inflater)

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
        val searchViewListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                loadData()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    loadData()
                }
                return true
            }

        }
        binding.searchName.setOnQueryTextListener(searchViewListener)
        binding.searchCode.setOnQueryTextListener(searchViewListener)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        loadData()
        if (fixedData()) {
            binding.filterLayout.visibility = View.GONE
            binding.swipeRefreshLayout.isEnabled = false
        }


    }

    private fun loadData() {
        val hasArgs = arguments != null
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            if (hasArgs) {
                val ids = requireArguments().getStringArrayList("ids")
                viewModel.loadFixedData(ids!!).collect { pagingData->
                    episodeAdapter.submitData(pagingData)
                }
            } else {
                buildFilter()
                viewModel.loadAllData(curFilter).collect{ pagingData->
                    episodeAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun buildFilter() {

        curFilter.name = binding.searchName.query.toString().ifEmpty { null }
        curFilter.episode = binding.searchCode.query.toString().ifEmpty { null }
    }

    private fun setupRv() {
        episodeAdapter = EpisodeAdapter()
        episodeAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                return@addLoadStateListener
            }
            if ( loadState.source.append is LoadState.NotLoading && loadState.prepend.endOfPaginationReached && episodeAdapter.itemCount < 1 ) {
                if (!noDataAlreadyShown) {
                    Toast.makeText(activity, "No data found", Toast.LENGTH_SHORT).show()
                }
            }
            if (binding.swipeRefreshLayout.isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            binding.progressBar.visibility = View.GONE
        }
        binding.recyclerView.apply {

            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            adapter = episodeAdapter
            setHasFixedSize(true)
        }
    }
    private fun fixedData(): Boolean {
        return arguments != null && requireArguments().getStringArrayList("ids") != null

    }

    companion object {
        @JvmStatic
        fun newInstance() = EpisodeFragment()
    }
}