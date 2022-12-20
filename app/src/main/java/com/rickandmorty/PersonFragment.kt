package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rickandmorty.databinding.FragmentMainScreenBinding
import com.rickandmorty.model.adapters.CharacterAdapter
import com.rickandmorty.model.character.CharactersService.CharactersListener
import com.rickandmorty.model.character.CharactersService.CharactersService
import com.rickandmorty.model.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonFragment : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var characterAdapter: CharacterAdapter
    private val viewModel: CharactersViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                characterAdapter.submitData(pagingData)


            }
        }
    }

    private fun setupRv() {
        characterAdapter = CharacterAdapter()
        binding.recyclerView.apply {

            layoutManager = StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
            )
            adapter = characterAdapter
            setHasFixedSize(true)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = PersonFragment()
    }
}