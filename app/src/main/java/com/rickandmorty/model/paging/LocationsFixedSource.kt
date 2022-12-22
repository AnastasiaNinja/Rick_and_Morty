package com.rickandmorty.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.ResultsLocation

class LocationsFixedSource(
    private val apiService: ApiService,
    private val links: List<String>
): PagingSource<Int, ResultsLocation>() {

    override fun getRefreshKey(state: PagingState<Int, ResultsLocation>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsLocation> {
        return try {
            val episodesIds = links.joinToString(",") {
                it.substringAfterLast("/")
            }
            val response = apiService.getSomeLocations(episodesIds)

            LoadResult.Page(
                data = response.body()!!,
                prevKey = null,
                nextKey = null
            )

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}