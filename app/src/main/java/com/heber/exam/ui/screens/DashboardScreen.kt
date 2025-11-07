package com.heber.exam.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.heber.exam.data.Class.User
import com.heber.exam.ui.viewmodel.DashboardViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onNavigateToThemeSettings: () -> Unit,
    onNavigateToCreateUser: () -> Unit
) {
    val users by viewModel.users.collectAsState()
    val isDarkTheme by viewModel.esModoOscuro.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme) Color.Black else Color.White)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de bienvenida
        Text(
            text = "Hola mucho gusto,\nbienvenido a mi app Bv",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkTheme) Color.White else Color.Black,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        // Indicador de modo actual (sin funcionalidad)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkTheme) Color(0xFF6B73FF) else Color(0xFF9FA8DA)
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isDarkTheme) "Modo Noche" else "Modo Día",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }

        // Botón Cambiar de Modo (navegación a configuraciones)
        Button(
            onClick = onNavigateToThemeSettings,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3F51B5)
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Cambiar de Modo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        // Botón Agregar Email
        Button(
            onClick = onNavigateToCreateUser,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
            .padding(bottom = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3F51B5)
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Agregar Email",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        // Sección de emails
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Mis Emails (${users.size})",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (users.isEmpty()) {
                // Mensaje cuando no hay emails
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color(0xFFE8E8E8)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "¡No, papu, No hay emails guardados!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkTheme) Color.White else Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Toca el botón 'Agregar Email' para empezar",
                            fontSize = 13.sp,
                            color = if (isDarkTheme) Color.Gray else Color.DarkGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                // Lista de emails cuando hay datos - usando LazyColumn para scroll independiente
                LazyColumn(
                    modifier = Modifier.height(300.dp), // Altura fija para evitar conflictos de scroll
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(users) { user ->
                        UserItem(
                            user = user,
                            onDelete = { viewModel.deleteUser(user) },
                            isDarkTheme = isDarkTheme
                        )
                    }
                }
            }
        }

        // Espaciado final para asegurar que todo sea visible
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun UserItem(
    user: User,
    onDelete: () -> Unit,
    isDarkTheme: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color(0xFFF5F5F5)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isDarkTheme) Color.White else Color.Black,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Creado: ${formatDate(user.createdAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDarkTheme) Color.Gray else Color.DarkGray
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = if (isDarkTheme) Color.Red else Color(0xFFD32F2F)
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formatter.format(Date(timestamp))
}
