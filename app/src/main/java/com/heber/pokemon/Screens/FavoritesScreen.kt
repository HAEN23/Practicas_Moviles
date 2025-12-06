package com.heber.pokemon.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.heber.pokemon.model.Pokemon
import com.heber.pokemon.ViewModels.PokemonViewModel
import com.heber.pokemon.util.getPokemonImageUrl

@Composable
fun FavoritesScreen(
    viewModel: PokemonViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val favoritePokemons by viewModel.favoritePokemons.collectAsState()
    val pokemons = favoritePokemons.map { Pokemon(name = it.name, url = it.url) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        PokemonList(
            pokemons = pokemons,
            onPokemonClick = { pokemon ->
                navController.navigate("pokemonDetails/${pokemon.name}")
            },
            onRemoveFavorite = { pokemon ->
                viewModel.removeFavorite(pokemon)
            },
            modifier = Modifier
        )
    }
}
