package com.heber.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heber.pokemon.data.PokemonDatabase
import com.heber.pokemon.Screens.FavoritesScreen
import com.heber.pokemon.Screens.PokemonDetailsScreen
import com.heber.pokemon.Screens.PokemonListScreen
import com.heber.pokemon.ViewModels.PokemonViewModel
import com.heber.pokemon.ViewModels.PokemonViewModelFactory
import com.heber.pokemon.Screens.SearchScreen
import com.heber.pokemon.ui.theme.PokemonTheme

class MainActivity : ComponentActivity() {
    private val db by lazy { PokemonDatabase.getDatabase(applicationContext) }
    private val viewModel: PokemonViewModel by viewModels { PokemonViewModelFactory(db) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "pokemonList",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("pokemonList") {
                            PokemonListScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable("search") {
                            SearchScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable("favorites") {
                            FavoritesScreen(
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                        composable("pokemonDetails/{pokemonName}") { backStackEntry ->
                            val pokemonName = backStackEntry.arguments?.getString("pokemonName")
                            if (pokemonName != null) {
                                PokemonDetailsScreen(
                                    viewModel = viewModel,
                                    pokemonName = pokemonName,
                                    navController = navController,
                                    pokemonDetailsState = viewModel.pokemonDetails
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
