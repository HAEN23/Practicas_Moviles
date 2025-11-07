package com.heber.exam.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.heber.exam.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserScreen(
    viewModel: DashboardViewModel,
    onNavigateBack: () -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var isEmailError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Usuario") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailError = false
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                isError = isEmailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                supportingText = if (isEmailError) {
                    { Text("Por favor ingresa un email válido") }
                } else null
            )

            Button(
                onClick = {
                    val emailText = email.text.trim()
                    if (emailText.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                        isEmailError = true
                    } else {
                        viewModel.addUser(emailText)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Usuario")
            }
        }
    }
}
