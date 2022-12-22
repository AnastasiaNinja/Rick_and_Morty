package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.R
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rickandmorty.databinding.FragmentMainEpisodeBinding
import com.rickandmorty.databinding.FragmentMainLocationBinding
import com.rickandmorty.databinding.FragmentMainPersonBinding
import com.rickandmorty.databinding.FragmentMainScreenBinding
import com.rickandmorty.model.adapters.CharacterAdapter
import com.rickandmorty.model.adapters.EpisodeAdapter
import com.rickandmorty.model.adapters.LocationAdapter
import com.rickandmorty.model.api.CharacterFilterParams
import com.rickandmorty.model.api.EpisodeFilterParams
import com.rickandmorty.model.api.LocationFilterParams
import com.rickandmorty.model.viewmodel.LocationsPagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : Fragment() {
    lateinit var binding: FragmentMainLocationBinding
    private lateinit var locationAdapter: LocationAdapter
    private val viewModel: LocationsPagingViewModel by viewModels()
    private var noDataAlreadyShown = false
    private var filterApplied = false
    private var curFilter = LocationFilterParams(null, null)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainLocationBinding.inflate(inflater)

        binding.applyFilterButton.setOnClickListener {
            filterApplied = true
            loadData()
        }

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
                    locationAdapter.submitData(pagingData)
                }
            } else {
                buildFilter()
                viewModel.loadAllData(curFilter).collect{ pagingData->
                    locationAdapter.submitData(pagingData)
                }
            }
        }
    }
    private fun buildFilter() {
        if (filterApplied) {
            val type = binding.type.text.toString().ifEmpty { null }
            val dimension = binding.dimension.text.toString().ifEmpty { null }
            curFilter = LocationFilterParams(type, dimension)
            filterApplied = false
        }
        curFilter.name = binding.searchName.query.toString()
    }

    private fun setupRv() {
        locationAdapter = LocationAdapter()
        locationAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                return@addLoadStateListener
            }
            if ( loadState.source.append is LoadState.NotLoading && loadState.prepend.endOfPaginationReached && locationAdapter.itemCount < 1 ) {
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
            adapter = locationAdapter
            setHasFixedSize(true)
        }
    }
    private fun fixedData(): Boolean {
        return arguments != null && requireArguments().getStringArrayList("ids") != null

    }

    companion object {
        @JvmStatic
        fun newInstance() = LocationFragment()
    }
}