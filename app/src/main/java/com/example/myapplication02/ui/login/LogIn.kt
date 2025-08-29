package com.example.myapplication02.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField // Changed from TextField for better visuals
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation // For password field
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication02.MAIN_SCREEN_ROOT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIn(
    modifier: Modifier = Modifier, // Keep the modifier parameter
    navController: NavHostController, // Removed default rememberNavController, as it's passed from NavHost
    id: MutableState<String> = remember { mutableStateOf("") },
    password: MutableState<String> = remember { mutableStateOf("") },
) {
//    var id by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(), // Apply the passed modifier
        topBar = { TopAppBar(title = { Text("로그인") }) }, // Changed title to "로그인"
        // Removed contentColor = Color.Yellow for a more standard appearance
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding) // Apply innerPadding from Scaffold
                .padding(16.dp) // Add overall padding for the content
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("ㄹㄱㅇ.", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = id.value,
                onValueChange = { newId -> id.value = newId },
                label = { Text("아이디") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { newPassword -> password.value = newPassword },
                label = { Text("비밀번호") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(), // Hides password characters
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Implement actual login logic here
                    // For now, just navigate to the main screen
                    navController.navigate(MAIN_SCREEN_ROOT) {
                        // Optional: Clear back stack up to home if login is successful
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true // Avoid multiple copies of home screen
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("로그인")
            }
        }
    }
}
