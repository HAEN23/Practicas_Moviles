package com.heber.examen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.heber.examen.data.Class.User
import com.heber.examen.data.repository.UserRepository
import com.heber.examen.ui.theme.ThemeManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repositorioUsuarios: UserRepository,
    private val gestorTema: ThemeManager
) : ViewModel() {

    private val _estadoInterfaz = MutableStateFlow(DashboardUiState())
    val estadoInterfaz: StateFlow<DashboardUiState> = _estadoInterfaz.asStateFlow()

    val listaEmails: StateFlow<List<User>> = repositorioUsuarios.getAllUsers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val esModoOscuro: StateFlow<Boolean> = gestorTema.isDarkTheme
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun cambiarTema() {
        viewModelScope.launch {
            gestorTema.setDarkTheme(!esModoOscuro.value)
        }
    }

    fun crearEmail(correo: String) {
        viewModelScope.launch {
            try {
                _estadoInterfaz.value = _estadoInterfaz.value.copy(isLoading = true, error = null)
                val nuevoUsuario = User(email = correo)
                repositorioUsuarios.insertUser(nuevoUsuario)
                _estadoInterfaz.value = _estadoInterfaz.value.copy(isLoading = false, userCreated = true)
            } catch (excepcion: Exception) {
                _estadoInterfaz.value = _estadoInterfaz.value.copy(
                    isLoading = false,
                    error = "¡Ups! Algo salió mal: ${excepcion.message}"
                )
            }
        }
    }

    fun eliminarEmail(usuario: User) {
        viewModelScope.launch {
            try {
                repositorioUsuarios.deleteUser(usuario)
            } catch (excepcion: Exception) {
                _estadoInterfaz.value = _estadoInterfaz.value.copy(
                    error = "¡Ay no! No pude eliminarlo: ${excepcion.message}"
                )
            }
        }
    }

    fun limpiarError() {
        _estadoInterfaz.value = _estadoInterfaz.value.copy(error = null)
    }

    fun limpiarEmailCreado() {
        _estadoInterfaz.value = _estadoInterfaz.value.copy(userCreated = false)
    }


    class Fabrica(
        private val repositorio: UserRepository,
        private val gestor: ThemeManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                return DashboardViewModel(repositorio, gestor) as T
            }
            throw IllegalArgumentException("Clase de ViewModel desconocida")
        }
    }
}

data class DashboardUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val userCreated: Boolean = false
)

