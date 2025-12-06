package com.heber.pokemon.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.heber.pokemon.ViewModels.PokemonViewModel
import androidx.navigation.NavController

@Composable
fun PokemonDetailsScreen(
    viewModel: PokemonViewModel,
    pokemonName: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(pokemonName) {
        viewModel.fetchPokemonDetails(pokemonName)
    }

    val pokemonDetails = viewModel.pokemonDetails
    val favoritePokemons by viewModel.favoritePokemons.collectAsState()
    val isFavorite = favoritePokemons.any { it.name == pokemonName }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            pokemonDetails?.let { details ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${details.id}.png",
                            contentDescription = details.name,
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = details.name.replaceFirstChar { it.uppercase() },
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = {
                                if (isFavorite) {
                                    viewModel.removeFavorite(details)
                                } else {
                                    viewModel.addFavorite(details)
                                }
                            }) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite) Color.Red else Color.Gray
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Details", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Height", fontWeight = FontWeight.Bold)
                                Text(text = "${details.height / 10.0} m")
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Weight", fontWeight = FontWeight.Bold)
                                Text(text = "${details.weight / 10.0} kg")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Abilities", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        details.abilities.forEach { ability ->
                            Text(text = "- ${ability.ability.name.replaceFirstChar { it.uppercase() }}")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Stats", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        details.stats.forEach { stat ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = stat.stat.name.replaceFirstChar { it.uppercase() })
                                Text(text = "${stat.base_stat}")
                            }
                        }
                    }
                }
            } ?: CircularProgressIndicator()
        }
    }
}
