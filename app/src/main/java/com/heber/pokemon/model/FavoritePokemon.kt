package com.heber.pokemon.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemons")
data class FavoritePokemon(
    @PrimaryKey
    val name: String,
    val url: String
)

