package com.pieterv.uncaught.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokedex")
data class PokedexListEntry(
    val pokemonName: String,
    val imageUrl: String,
    @PrimaryKey
    val number: String,
    val isCaught: Boolean
)