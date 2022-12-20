package com.rickandmorty.model.api

import com.example.example.InfoLocation
import com.example.example.ResultsLocation
import com.google.gson.annotations.SerializedName


data class ResponseApiLocation (

    @SerializedName("info"    ) var info    : InfoLocation?              = InfoLocation(),
    @SerializedName("results" ) var results : List<ResultsLocation> = emptyList()

)