package com.heber.pokemon.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heber.pokemon.data.PokemonDatabase
import com.heber.pokemon.model.FavoritePokemon
import com.heber.pokemon.model.Pokemon
import com.heber.pokemon.model.PokemonDetails
import com.heber.pokemon.network.RetrofitInstance
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PokemonViewModel(private val db: PokemonDatabase) : ViewModel() {
    private val _pokemons = mutableStateOf<List<Pokemon>>(emptyList())
    val pokemons: List<Pokemon>
        get() = _pokemons.value

    private val _pokemonDetails = mutableStateOf<PokemonDetails?>(null)
    val pokemonDetails: State<PokemonDetails?> = _pokemonDetails

    val favoritePokemons = db.favoritePokemonDao().getFavoritePokemons()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        fetchPokemons()
    }

    private fun fetchPokemons() {
        viewModelScope.launch {
            try {
                _pokemons.value = RetrofitInstance.api.getPokemons(limit = 2000).results
            } catch (e: Exception) {
                Log.e("PokemonViewModel", "Error fetching pokemons", e)
            }
        }
    }

    fun fetchPokemonDetails(name: String) {
        viewModelScope.launch {
            try {
                _pokemonDetails.value = RetrofitInstance.api.getPokemonDetails(name)
            } catch (e: Exception) {
                Log.e("PokemonViewModel", "Error fetching details for $name", e)
            }
        }
    }

    fun clearPokemonDetails() {
        _pokemonDetails.value = null
    }

    fun addFavorite(pokemon: Pokemon) {
        viewModelScope.launch {
            db.favoritePokemonDao().addFavorite(FavoritePokemon(pokemon.name, pokemon.url))
        }
    }

    fun addFavorite(pokemonDetails: PokemonDetails) {
        viewModelScope.launch {
            val pokemon = FavoritePokemon(
                name = pokemonDetails.name,
                url = "https://pokeapi.co/api/v2/pokemon/${pokemonDetails.id}/"
            )
            db.favoritePokemonDao().addFavorite(pokemon)
        }
    }

    fun removeFavorite(pokemon: Pokemon) {
        viewModelScope.launch {
            db.favoritePokemonDao().removeFavorite(FavoritePokemon(pokemon.name, pokemon.url))
        }
    }

    fun removeFavorite(pokemon: FavoritePokemon) {
        viewModelScope.launch {
            db.favoritePokemonDao().removeFavorite(pokemon)
        }
    }

    fun removeFavorite(pokemonDetails: PokemonDetails) {
        viewModelScope.launch {
            val pokemon = FavoritePokemon(
                name = pokemonDetails.name,
                url = "https://pokeapi.co/api/v2/pokemon/${pokemonDetails.id}/"
            )
            db.favoritePokemonDao().removeFavorite(pokemon)
        }
    }
}