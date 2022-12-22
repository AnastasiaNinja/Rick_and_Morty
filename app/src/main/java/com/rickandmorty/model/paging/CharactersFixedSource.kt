package com.rickandmorty.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.ResultsCharacters

class CharactersFixedSource(
    private val apiService: ApiService,
    private val links: List<String>
): PagingSource<Int, ResultsCharacters>() {

    override fun getRefreshKey(state: PagingState<Int, ResultsCharacters>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsCharacters> {
        return try {
            val characterIds = links.joinToString(",") {
                it.substringAfterLast("/")
            }
            val response = apiService.getSomeCharacters(characterIds)

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