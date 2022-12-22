package com.rickandmorty.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.ResultsCharacters
import com.rickandmorty.model.api.ResultsEpisode
import com.rickandmorty.model.paging.CharactersFixedSource
import com.rickandmorty.model.paging.EpisodesFixedSource
import com.rickandmorty.model.paging.EpisodesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EpisodesPagingViewModel
@Inject constructor(private val apiService: ApiService): ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 1)){
        EpisodesPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)

    fun loadFixedData(links: List<String>) : Flow<PagingData<ResultsEpisode>> {
        return Pager(PagingConfig(pageSize = 1)){
            EpisodesFixedSource(apiService, links)
        }.flow.cachedIn(viewModelScope)
    }
}