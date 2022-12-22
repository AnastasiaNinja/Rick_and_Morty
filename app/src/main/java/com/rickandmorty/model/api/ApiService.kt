package com.rickandmorty.model.api

import com.rickandmorty.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT_CHARACTER)
    suspend fun getAllCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
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
    suspend fun getCharacterDetails(@Path("id") id: Long
    ): Response<ResultsCharacters>

    @GET(Constants.END_POINT_EPISODE_DETAILS)
    suspend fun getEpisodeDetails(@Path("id") id: Long
    ): Response<ResultsEpisode>

    @GET(Constants.END_POINT_LOCATION_DETAILS)
    suspend fun getLocationDetails(@Path("id") id: Long
    ): Response<ResultsLocation>

    @GET(Constants.END_POINT_SOME_CHARACTERS)
    suspend fun getSomeCharacters(
        @Path("ids") someIds: String
    ): Response<List<ResultsCharacters>>

    @GET(Constants.END_POINT_SOME_EPISODES)
    suspend fun getSomeEpisodes(
        @Path("ids") someIds: String
    ): Response<List<ResultsEpisode>>

    @GET(Constants.END_POINT_SOME_EPISODES)
    suspend fun getSomeLocations(
        @Path("ids") someIds: String
    ): Response<List<ResultsLocation>>


}