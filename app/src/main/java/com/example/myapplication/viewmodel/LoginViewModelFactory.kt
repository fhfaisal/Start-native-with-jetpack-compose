package com.example.myapplication.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory(
    private val snackbarHostState: SnackbarHostState,
    private val onNavigateHome: () -> Unit
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(snackbarHostState, onNavigateHome) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
