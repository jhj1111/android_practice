package com.example.myapplication02.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication02.AddMemo
import com.example.myapplication02.AddMemoViewModel
import com.example.myapplication02.ToDoList
import com.example.myapplication02.GreetingMain
import com.example.myapplication02.LOGIN_SCREEN_ROOT
import com.example.myapplication02.LogIn
import com.example.myapplication02.MAIN_SCREEN_ROOT
import com.example.myapplication02.CREAT_MEMO_ROOT
import com.example.myapplication02.MemoList
import com.example.myapplication02.SCREEN02_SCREEN_ROOT

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
    addMemo: AddMemoViewModel = viewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = MAIN_SCREEN_ROOT,
        modifier = modifier // Apply the modifier (containing padding or fillMaxSize) to NavHost
    ) {
        composable(MAIN_SCREEN_ROOT) { // Will be correctly padded by NavHost
//            GreetingMain(
//                name = "", // Default name
//                navController = navController,
//                count = countMainButton,
//                onIncrementCount = onIncrementCount,
//                // modifier parameter of GreetingMain will use its default (Modifier)
//            )
            MemoList(
                navController = navController,
                memoList = addMemo
            )
        }

        composable("$MAIN_SCREEN_ROOT/{value}") { backStackEntry ->
            val value = backStackEntry.arguments?.getString("value") ?: ""

//            GreetingMain(
//                name = value,
//                navController = navController,
//                count = countMainButton,
//                onIncrementCount = onIncrementCount,
//            )
            MemoList(
                navController = navController,
                memoList = addMemo
            )
        }

        composable("$MAIN_SCREEN_ROOT/{id}/{password}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""

//            GreetingMain(
//                id = id,
//                password = password,
//                navController = navController,
//                count = countMainButton,
//                onIncrementCount = onIncrementCount,
//            )
            MemoList(
                navController = navController,
                memoList = addMemo
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

        composable(CREAT_MEMO_ROOT) {
            AddMemo( // Assuming Screen is defined in AddMemo.kt
                navController = navController,
                addMemo = addMemo,
            )
        }

        composable(SCREEN02_SCREEN_ROOT) {
            ToDoList(
                navController = navController,
            )
        }
    }
}