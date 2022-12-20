package com.rickandmorty.model.api

import com.google.gson.annotations.SerializedName


data class ResponseApiEpisode (

  @SerializedName("info"    ) var info    : InfoEpisode?              = InfoEpisode(),
  @SerializedName("results" ) var results : List<ResultsEpisode> = emptyList()

)