package com.heber.pokemon.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heber.pokemon.data.PokemonDatabase

class PokemonViewModelFactory(private val db: PokemonDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}