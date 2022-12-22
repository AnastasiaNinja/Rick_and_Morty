package com.rickandmorty.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.model.api.ResultsEpisode
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.EpisodeFilterParams


class EpisodesPagingSource(
    private val apiService: ApiService,
    private val filter: EpisodeFilterParams
): PagingSource<Int, ResultsEpisode>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsEpisode>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsEpisode> {
        return try{
            val currentPage = params.key ?: 1
            val response = apiService.getAllEpisodes(
            page = currentPage, name = filter.name,
            episode = filter.episode
            )

            val data = response.body()?.results ?: emptyList()
            val responseData = mutableListOf<ResultsEpisode>()
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