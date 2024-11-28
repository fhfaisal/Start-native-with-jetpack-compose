
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.LoginViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    snackbarHostState: SnackbarHostState,
    onNavigateHome: () -> Unit,
    viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(snackbarHostState, onNavigateHome)
    )
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                var name by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }

                // Name Field
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        viewModel.nameError = null
                    },
                    label = { Text(text = "Name") },
                    isError = viewModel.nameError != null,
                    modifier = Modifier.fillMaxWidth()
                )
                viewModel.nameError?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        viewModel.emailError = null
                    },
                    label = { Text(text = "Email") },
                    isError = viewModel.emailError != null,
                    modifier = Modifier.fillMaxWidth()
                )
                viewModel.emailError?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Submit Button
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.validateAndSubmit(name, email) }
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}
