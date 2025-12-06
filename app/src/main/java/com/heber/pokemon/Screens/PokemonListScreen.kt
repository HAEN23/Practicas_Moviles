package com.heber.pokemon.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.heber.pokemon.model.Pokemon
import com.heber.pokemon.ViewModels.PokemonViewModel
import com.heber.pokemon.util.getPokemonImageUrl

@Composable
fun PokemonListScreen(
    viewModel: PokemonViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(8.dp)) {
        Button(
            onClick = { navController.navigate("search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 2.dp
            )
        ) {
            Icon(Icons.Default.Search, contentDescription = "Buscar")
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text("Buscar Pókemon")
        }
        Button(
            onClick = { navController.navigate("favorites") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 2.dp
            )
        ) {
            Icon(Icons.Default.Favorite, contentDescription = "Favoritos")
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text("Favoritos")
        }
        PokemonList(
            pokemons = viewModel.pokemons,
            onPokemonClick = { pokemon ->
                navController.navigate("pokemonDetails/${pokemon.name}")
            }
        )
    }
}


@Composable
fun PokemonList(
    pokemons: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit,
    modifier: Modifier = Modifier,
    onRemoveFavorite: ((Pokemon) -> Unit)? = null
) {
    LazyColumn(modifier = modifier) {
        items(pokemons) { pokemon ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onPokemonClick(pokemon) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = getPokemonImageUrl(pokemon.url),
                        contentDescription = pokemon.name,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = pokemon.name,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (onRemoveFavorite != null) {
                        IconButton(onClick = { onRemoveFavorite(pokemon) }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Eliminar de favoritos"
                            )
                        }
                    }
                }
            }
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}
