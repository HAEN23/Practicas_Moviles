package com.heber.examen.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.heber.examen.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen(
    viewModel: DashboardViewModel,
    onNavigateBack: () -> Unit
) {
    val estadoUI by viewModel.estadoInterfaz.collectAsState()

    var correoElectronico by remember { mutableStateOf("") }
    var errorCorreo by remember { mutableStateOf("") }


    LaunchedEffect(estadoUI.userCreated) {
        if (estadoUI.userCreated) {
            viewModel.limpiarEmailCreado()
            onNavigateBack()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("¡Nuevo Email!") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Cancelar"
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Agrega un nuevo email",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo Email
                    OutlinedTextField(
                        value = correoElectronico,
                        onValueChange = {
                            correoElectronico = it
                            errorCorreo = when {
                                it.trim().isEmpty() -> ""
                                !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() ->""
                                else -> ""
                            }
                        },
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        isError = errorCorreo.isNotEmpty(),
                        supportingText = if (errorCorreo.isNotEmpty()) {
                            { Text(errorCorreo) }
                        } else null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )
                }
            }

            //
            Button(
                onClick = {

                    val esCorreoValido = correoElectronico.trim().isNotEmpty() &&
                        android.util.Patterns.EMAIL_ADDRESS.matcher(correoElectronico).matches()

                    if (!esCorreoValido) errorCorreo = if (correoElectronico.trim().isEmpty()) {
                        ""
                    } else {
                        ""
                    }

                    if (esCorreoValido) {
                        viewModel.crearEmail(correo = correoElectronico.trim())
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !estadoUI.isLoading
            ) {
                if (estadoUI.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(if (estadoUI.isLoading) "Guardando..." else "¡Agregar Email!")
            }

            // Mostrar el error si es que existe
            estadoUI.error?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = error,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }


        }
    }
}
