package com.rickandmorty.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.CharacterFilterParams
import com.rickandmorty.model.api.ResultsCharacters

class CharactersPagingSource(
    private val apiService: ApiService,
    private val filter: CharacterFilterParams
) : PagingSource<Int, ResultsCharacters>() {

    override fun getRefreshKey(state: PagingState<Int, ResultsCharacters>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsCharacters> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getAllCharacters(
                page = currentPage, name = filter.name,
                status = filter.status, species = filter.species, type = filter.type,
                gender = filter.gender
            )
            val data = response.body()?.results ?: emptyList()
            val responseData = mutableListOf<ResultsCharacters>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}