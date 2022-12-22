package com.rickandmorty

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rickandmorty.databinding.FragmentMainPersonBinding
import com.rickandmorty.model.adapters.CharacterAdapter
import com.rickandmorty.model.api.CharacterFilterParams
import com.rickandmorty.model.viewmodel.CharactersPagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonFragment : Fragment() {
    private lateinit var binding: FragmentMainPersonBinding
    private lateinit var characterAdapter: CharacterAdapter
    private val viewModel: CharactersPagingViewModel by viewModels()
    private var filterApplied = false
    private var noDataAlreadyShown = false
    private var curFilter = CharacterFilterParams(null, null, null, null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPersonBinding.inflate(inflater)
        setUpSpinners()
        binding.applyFilterButton.setOnClickListener {
            filterApplied = true
            loadData()
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
        binding.searchName.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        })
        return binding.root
    }

    private fun setUpSpinners() {
        val status = arrayOf("all", "alive", "dead", "unknown")
        val gender = arrayOf("all", "female", "male", "genderless", "unknown")
        val statusAdapter = ArrayAdapter(
            requireActivity(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            status
        )
        val genderAdapter = ArrayAdapter(
            requireActivity(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            gender
        )
        binding.statusSpinner.adapter = statusAdapter
        binding.spinnerGender.adapter = genderAdapter
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
        noDataAlreadyShown = false
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            if (fixedData()) {
                val ids = requireArguments().getStringArrayList("ids")
                viewModel.loadFixedData(ids!!).collect { pagingData ->
                    characterAdapter.submitData(pagingData)
                }
            } else {
                buildFilter()
                viewModel.loadAllData(curFilter).collect { pagingData ->
                    characterAdapter.submitData(pagingData)
                }
            }

        }
    }

    private fun buildFilter() {
        if (filterApplied) {
            val statusInput = binding.statusSpinner.selectedItem.toString()
            val genderInput = binding.spinnerGender.selectedItem.toString()
            val status = if (statusInput == "all") null else statusInput
            val species = binding.speciesFilter.text.toString().ifEmpty { null }
            val type = binding.typeFilter.text.toString().ifEmpty { null }
            val gender = if (genderInput == "all") null else genderInput
            curFilter = CharacterFilterParams(status, species, type, gender)
            filterApplied = false
        }
        curFilter.name = binding.searchName.query.toString()
    }

    private fun setupRv() {
        characterAdapter = CharacterAdapter()
        characterAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                return@addLoadStateListener
            }
            if ( loadState.source.append is LoadState.NotLoading && loadState.prepend.endOfPaginationReached && characterAdapter.itemCount < 1 ) {
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
            adapter = characterAdapter
            setHasFixedSize(true)
        }
    }

    private fun fixedData(): Boolean {
        return arguments != null && requireArguments().getStringArrayList("ids") != null

    }

    companion object {

        @JvmStatic
        fun newInstance() = PersonFragment()
    }
}