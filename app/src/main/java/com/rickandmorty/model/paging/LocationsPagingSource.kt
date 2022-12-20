package com.rickandmorty.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.example.ResultsLocation
import com.rickandmorty.model.api.ApiService

class LocationsPagingSource(
    private val apiService: ApiService
    ): PagingSource<Int, ResultsLocation>() {
        override fun getRefreshKey(state: PagingState<Int, ResultsLocation>): Int? {
            return null
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsLocation> {

            return try{
                val currentPage = params.key ?: 1
                val response = apiService.getAllLocations(currentPage)
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