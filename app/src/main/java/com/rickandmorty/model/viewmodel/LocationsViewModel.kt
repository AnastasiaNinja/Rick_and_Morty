package com.rickandmorty.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.paging.LocationsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel
@Inject constructor(private val apiService: ApiService): ViewModel(){

    val listData = Pager(PagingConfig(pageSize = 1)){
        LocationsPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)
}