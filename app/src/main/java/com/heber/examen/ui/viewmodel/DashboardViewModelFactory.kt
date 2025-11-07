package com.heber.examen.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heber.examen.data.repository.UserRepository
import com.heber.examen.ui.theme.ThemeManager

class DashboardViewModelFactory(
    private val userRepository: UserRepository,
    private val themeManager: ThemeManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(userRepository, themeManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
