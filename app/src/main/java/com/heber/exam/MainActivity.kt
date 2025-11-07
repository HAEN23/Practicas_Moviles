package com.heber.exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heber.exam.data.repository.UserRepository
import com.heber.exam.ui.screens.CreateUserScreen
import com.heber.exam.ui.screens.DashboardScreen
import com.heber.exam.ui.screens.ThemeSettingsScreen
import com.heber.exam.ui.theme.ExamTheme
import com.heber.exam.ui.theme.ThemeManager
import com.heber.exam.ui.viewmodel.DashboardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamApp()
        }
    }
}

@Composable
fun ExamApp() {
    // Inicializar dependencias
    val application = ExamApplication.instance
    val database = application.database
    val userRepository = UserRepository(database.userDao())
    val themeManager = ThemeManager(application)


    val fabricaViewModel = DashboardViewModel.Fabrica(userRepository, themeManager)
    val dashboardViewModel: DashboardViewModel = viewModel(factory = fabricaViewModel)

    // Ver el tema actual
    val esModoOscuro by dashboardViewModel.esModoOscuro.collectAsState()

    ExamTheme(darkTheme = esModoOscuro) {
        val navController = rememberNavController()

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "dashboard",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("dashboard") {
                    DashboardScreen(
                        viewModel = dashboardViewModel,
                        onNavigateToThemeSettings = {
                            navController.navigate("theme_settings")
                        },
                        onNavigateToCreateUser = {
                            navController.navigate("create_user")
                        }
                    )
                }

                composable("theme_settings") {
                    ThemeSettingsScreen(
                        viewModel = dashboardViewModel,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable("create_user") {
                    CreateUserScreen(
                        viewModel = dashboardViewModel,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
