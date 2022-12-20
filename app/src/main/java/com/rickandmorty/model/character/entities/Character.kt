package com.rickandmorty.model.character.entities

import android.os.Parcel
import android.os.Parcelable

data class Character (
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)

