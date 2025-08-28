package com.example.myapplication02.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication02.Greeting02
import com.example.myapplication02.GreetingMain
import com.example.myapplication02.LOGIN_SCREEN_ROOT
import com.example.myapplication02.LogIn
import com.example.myapplication02.MAIN_SCREEN_ROOT
import com.example.myapplication02.SCREEN01_SCREEN_ROOT
import com.example.myapplication02.SCREEN02_SCREEN_ROOT
import com.example.myapplication02.Screen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier, // This will be Modifier.padding(innerPadding) or Modifier.fillMaxSize()
    countMainButton: Int,
    onIncrementCount: () -> Unit,
    text: MutableState<String>,
    id: MutableState<String>,
    password: MutableState<String>,
    onValueChange: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = MAIN_SCREEN_ROOT,
        modifier = modifier // Apply the modifier (containing padding or fillMaxSize) to NavHost
    ) {
        composable(MAIN_SCREEN_ROOT) { // Will be correctly padded by NavHost
            GreetingMain(
                name = "", // Default name
                navController = navController,
                count = countMainButton,
                onIncrementCount = onIncrementCount,
                // modifier parameter of GreetingMain will use its default (Modifier)
            )
        }

        composable("$MAIN_SCREEN_ROOT/{value}") { backStackEntry ->
            val value = backStackEntry.arguments?.getString("value") ?: ""

            GreetingMain(
                name = value,
                navController = navController,
                count = countMainButton,
                onIncrementCount = onIncrementCount,
            )
        }

        composable("$MAIN_SCREEN_ROOT/{id}/{password}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""

            GreetingMain(
                id = id,
                password = password,
                navController = navController,
                count = countMainButton,
                onIncrementCount = onIncrementCount,
            )
        }

        composable(LOGIN_SCREEN_ROOT) { // Will be fillMaxSize by NavHost
            LogIn(
                navController = navController,
                id = id,
                password = password,
                // modifier for LogIn will be its default. LogIn then uses Scaffold(Modifier.fillMaxSize())
            )
        }

        composable(SCREEN01_SCREEN_ROOT) {
            Screen( // Assuming Screen is defined in Screen01.kt
                navController = navController,
            )
        }

        composable(SCREEN02_SCREEN_ROOT) {
            Greeting02(
                name = SCREEN02_SCREEN_ROOT,
                navController = navController,
                value = text,
                onValueChange = onValueChange
            )
        }
    }
}