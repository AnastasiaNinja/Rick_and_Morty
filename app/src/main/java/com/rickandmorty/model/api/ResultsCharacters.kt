package com.rickandmorty.model.api

import com.google.gson.annotations.SerializedName

data class ResultsCharacters (

  @SerializedName("id"       ) var id: Long,
  @SerializedName("name"     ) var name     : String?           = null,
  @SerializedName("status"   ) var status   : String?           = null,
  @SerializedName("species"  ) var species  : String?           = null,
  @SerializedName("type"     ) var type     : String?           = null,
  @SerializedName("gender"   ) var gender   : String?           = null,
  @SerializedName("image"    ) var image    : String?           = null,
  @SerializedName("episode"  ) var episode  : ArrayList<String> = arrayListOf(),
  @SerializedName("url"      ) var url      : String?           = null,
  @SerializedName("created"  ) var created  : String?           = null,
  @SerializedName("location" ) var location : Any?,
  @SerializedName("origin"   ) var origin   : Any?

)