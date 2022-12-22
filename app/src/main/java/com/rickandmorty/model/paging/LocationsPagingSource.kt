package com.rickandmorty.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.model.api.ResultsLocation
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.CharacterFilterParams
import com.rickandmorty.model.api.LocationFilterParams

class LocationsPagingSource(
    private val apiService: ApiService,
    private val filter: LocationFilterParams
    ): PagingSource<Int, ResultsLocation>() {
        override fun getRefreshKey(state: PagingState<Int, ResultsLocation>): Int? {
            return null
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsLocation> {

            return try{
                val currentPage = params.key ?: 1
                val response = apiService.getAllLocations(
                    page = currentPage, name = filter.name,
                    type = filter.type, dimension = filter.dimension
                )


                val data = response.body()?.results?: emptyList()
                val responseData = mutableListOf<ResultsLocation>()
                responseData.addAll(data)

                LoadResult.Page(
                    data = responseData,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = currentPage.plus(1)
                )

            } catch (e: Exception){
                LoadResult.Error(e)
            }
        }
}