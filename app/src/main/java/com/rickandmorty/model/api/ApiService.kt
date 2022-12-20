package com.rickandmorty.model.api

import com.rickandmorty.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT_CHARACTER)
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): Response<ResponseApiCharacters>

    @GET(Constants.END_POINT_EPISODE)
    suspend fun getAllEpisodes(
        @Query("page") page: Int
    ): Response<ResponseApiEpisode>

    @GET(Constants.END_POINT_LOCATION)
    suspend fun getAllLocations(
         @Query("page") page: Int
    ): Response<ResponseApiLocation>

    @GET(Constants.END_POINT_CHARACTER_DETAILS)
    suspend fun getCharacterDetails(@Path("id") id: Long)
    : Response<ResultsCharacters>
}