package com.rickandmorty.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.ResultsEpisode

class EpisodesFixedSource(
    private val apiService: ApiService,
    private val links: List<String>
): PagingSource<Int, ResultsEpisode>() {

    override fun getRefreshKey(state: PagingState<Int, ResultsEpisode>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsEpisode> {
        return try {
            val episodesIds = links.joinToString(",") {
                it.substringAfterLast("/")
            }
            val response = apiService.getSomeEpisodes(episodesIds)

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