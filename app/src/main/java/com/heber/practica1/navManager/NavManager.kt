package com.heber.practica1.navManager

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.heber.practica1.views.DetailsView
import com.heber.practica1.views.HomeView

@Composable
fun NavManager(){
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "Home"
    ){
        composable("Home") {
            HomeView(navController)
        }
        composable("Details/{id}", arguments = listOf(
            navArgument(name="id"){
                type = NavType.LongType
            }
        )) {
            val id = it.arguments?.getLong("id") ?: 0
            DetailsView(navController, id)
        }
    }
}