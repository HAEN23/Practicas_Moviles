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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.heber.pokemon.ViewModels.PokemonViewModel
import com.heber.pokemon.util.getPokemonImageUrl

@Composable
fun SearchScreen(
    viewModel: PokemonViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val pokemons = viewModel.pokemons.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }
    val favoritePokemons by viewModel.favoritePokemons.collectAsState()

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar Pokémon") },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                singleLine = true
            )
        }
        LazyColumn {
            items(pokemons) { pokemon ->
                val isFavorite = favoritePokemons.any { it.name == pokemon.name }
                ListItem(
                    headlineContent = { Text(pokemon.name) },
                    leadingContent = {
                        AsyncImage(
                            model = getPokemonImageUrl(pokemon.url),
                            contentDescription = pokemon.name,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    modifier = Modifier.clickable {
                        navController.navigate("pokemonDetails/${pokemon.name}")
                    },
                    trailingContent = {
                        IconButton(onClick = {
                            if (isFavorite) {
                                viewModel.removeFavorite(pokemon)
                            } else {
                                viewModel.addFavorite(pokemon)
                            }
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorite"
                            )
                        }
                    }
                )
            }
        }
    }
}
