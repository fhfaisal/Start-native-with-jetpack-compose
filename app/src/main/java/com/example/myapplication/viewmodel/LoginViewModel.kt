package com.example.myapplication.viewmodel
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.utils.AppValidators
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val snackbarHostState: SnackbarHostState,
    private val onNavigateHome: () -> Unit
) : ViewModel() {

    var nameError: String? by mutableStateOf(null)
    var emailError: String? by mutableStateOf(null)

    fun validateAndSubmit(name: String, email: String) {
        nameError = AppValidators.validateEmptyText("Name", name)
        emailError = AppValidators.validateEmail(email)

        if (nameError == null && emailError == null) {
            // Show success Snackbar and perform navigation
            showSnackbarWithAction(
                "Login Successful",
                "Go Home",
                onNavigateHome
            )
        } else {
            // Show error Snackbar
            showSnackbarWithAction(
                "Please fix the errors and try again.",
                "Dismiss"
            ) {}
        }
    }

    private fun showSnackbarWithAction(
        message: String,
        actionLabel: String,
        action: () -> Unit
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = SnackbarDuration.Short
            ).let { result ->
                if (result == SnackbarResult.ActionPerformed) {
                    action()
                }
            }
        }
    }
}

