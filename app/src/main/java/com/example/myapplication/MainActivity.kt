package com.example.myapplication


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.AppValidators
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MyComposable()
            }
        }
    }
}

@Composable
fun MyComposable() {
    var myValue by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            //FormValidationExample()
            FormValidationExampleWithSnackbar()
            AppHelper.HeightBetweenItem(height = 10.dp)
//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.Center
//            ) {
//                Button(
//                    shape = ButtonDefaults.elevatedShape,
//                    border = ButtonDefaults.outlinedButtonBorder,
//                    onClick = { myValue = !myValue }) {
//                    Text(text = if (myValue) "Login" else "Sign")
//                }
//            }
        }
    }
}

@Composable
fun TwoInputFieldsInRow() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") },
            placeholder = {
                Text(
                    text = "Please enter your name",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary)
                )
            }
            //modifier = Modifier.weight(1f) // Equal width for both fields
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            placeholder = {
                Text(
                    text = "Please enter your email",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary)
                )
            }
            //modifier = Modifier.weight(1f) // Equal width for both fields
        )
    }
}
@Composable
fun FormValidationExampleWithSnackbar() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Snackbar Host
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Name Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                name = it
                nameError = null
            },
            label = { Text(text = "Name") },
            placeholder = { Text("Please enter your name") },
            isError = nameError != null
        )
        if (nameError != null) {
            Text(
                text = nameError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
                emailError = null
            },
            label = { Text(text = "Email") },
            placeholder = { Text("Please enter your email") },
            isError = emailError != null
        )
        if (emailError != null) {
            Text(
                text = emailError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                nameError = AppValidators.validateEmptyText("Name", name)
                emailError = AppValidators.validateEmail(email)

                if (nameError == null && emailError == null) {
                    // Show success Snackbar with action
                    showSnackbarWithAction(
                        snackbarHostState,
                        "Login Successful",
                        "Go Home"
                    ) {
                        // Perform your navigation action here (e.g., navigate to home)
                        //navController.navigate("home")
                    }
                } else {
                    // Show error Snackbar
                    showSnackbarWithAction(
                        snackbarHostState,
                        "Please fix the errors and try again.",
                        "Dismiss"
                    ) {}
                }
            }
        ) {
            Text(text = "Submit")
        }
    }
}

// Helper function to show Snackbar
private fun showSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String
) {
    CoroutineScope(Dispatchers.Main).launch {
        snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short
        )
    }
}
// Helper function to show Snackbar with action
private fun showSnackbarWithAction(
    snackbarHostState: SnackbarHostState,
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

@Composable
fun FormValidationExample() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // State to store errors
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Name Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                name = it
                nameError = null // Reset error when text changes
            },
            label = { Text(text = "Name") },
            placeholder = { Text("Please enter your name") },
            isError = nameError != null
        )
        if (nameError != null) {
            Text(
                text = nameError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = {
                email = it
                emailError = null // Reset error when text changes
            },
            label = { Text(text = "Email") },
            placeholder = { Text("Please enter your email") },
            isError = emailError != null
        )
        if (emailError != null) {
            Text(
                text = emailError ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                // Validate name
                nameError = AppValidators.validateEmptyText("Name", name)

                // Validate email
                emailError = AppValidators.validateEmail(email)

                // If no errors, perform the desired action
                if (nameError == null && emailError == null) {
                    Log.d("FormValidation", "Form is valid! Name: $name, Email: $email")
                }
            }
        ) {
            Text(text = "Submit")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposable()
}

object AppHelper {
    @Composable
    fun HeightBetweenItem(height: Dp? = null) {
        Spacer(modifier = Modifier.height(height ?: 20.dp))
    }

    @Composable
    fun WidthBetweenItem(width: Dp? = null) {
        Spacer(modifier = Modifier.height(width ?: 20.dp))
    }
}

