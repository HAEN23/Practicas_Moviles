package com.heber.exam.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.heber.exam.data.Class.User
import com.heber.exam.data.repository.UserRepository
import com.heber.exam.ui.theme.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val userRepository: UserRepository,
    private val themeManager: ThemeManager
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    val esModoOscuro: StateFlow<Boolean> = themeManager.isDarkTheme

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            userRepository.getAllUsers().collect {
                _users.value = it
            }
        }
    }

    fun addUser(email: String) {
        viewModelScope.launch {
            val user = User(email = email)
            userRepository.insertUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

    fun toggleTheme() {
        themeManager.toggleTheme()
    }

    class Fabrica(
        private val userRepository: UserRepository,
        private val themeManager: ThemeManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                return DashboardViewModel(userRepository, themeManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
