package com.rickandmorty.model.api

import com.google.gson.annotations.SerializedName


data class ResponseApiCharacters (

    @SerializedName("results" ) var results : List<ResultsCharacters> = emptyList()

)