package com.rickandmorty.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.CharacterFilterParams
import com.rickandmorty.model.api.ResultsCharacters
import com.rickandmorty.model.paging.CharactersFixedSource
import com.rickandmorty.model.paging.CharactersPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersPagingViewModel
@Inject constructor(private val apiService: ApiService):ViewModel(){

    fun loadAllData(filter: CharacterFilterParams) : Flow<PagingData<ResultsCharacters>> {
        return Pager(PagingConfig(pageSize = 1)){
            CharactersPagingSource(apiService, filter)
        }.flow.cachedIn(viewModelScope)
    }

    fun loadFixedData(links: List<String>) : Flow<PagingData<ResultsCharacters>> {
        return Pager(PagingConfig(pageSize = 1)){
            CharactersFixedSource(apiService, links)
        }.flow.cachedIn(viewModelScope)
    }
}