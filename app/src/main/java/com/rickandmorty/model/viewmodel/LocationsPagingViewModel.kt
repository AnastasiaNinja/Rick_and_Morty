package com.rickandmorty.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rickandmorty.model.api.*
import com.rickandmorty.model.paging.LocationsFixedSource
import com.rickandmorty.model.paging.LocationsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LocationsPagingViewModel
@Inject constructor(private val apiService: ApiService): ViewModel(){

    fun loadAllData(filter: LocationFilterParams) : Flow<PagingData<ResultsLocation>> {
        return Pager(PagingConfig(pageSize = 1)){
            LocationsPagingSource(apiService, filter)
        }.flow.cachedIn(viewModelScope)
    }

    fun loadFixedData(links: List<String>) : Flow<PagingData<ResultsLocation>> {
        return Pager(PagingConfig(pageSize = 1)){
            LocationsFixedSource(apiService, links)
        }.flow.cachedIn(viewModelScope)
    }
}