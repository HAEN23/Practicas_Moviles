package com.heber.pokemon.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.heber.pokemon.model.FavoritePokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePokemonDao {
    @Query("SELECT * FROM favorite_pokemons")
    fun getFavoritePokemons(): Flow<List<FavoritePokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(pokemon: FavoritePokemon)

    @Delete
    suspend fun removeFavorite(pokemon: FavoritePokemon)
}

