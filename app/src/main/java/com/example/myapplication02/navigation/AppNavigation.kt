package com.example.myapplication02.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication02.ADMIN_USER_LIST_ROOT
import com.example.myapplication02.CREATE_USER_ROOT
import com.example.myapplication02.ui.memolist.AddMemo
import com.example.myapplication02.ui.memolist.AddMemoViewModel
import com.example.myapplication02.ui.todolist.ToDoList
import com.example.myapplication02.LOGIN_SCREEN_ROOT
import com.example.myapplication02.ui.login.LogIn
import com.example.myapplication02.MAIN_SCREEN_ROOT
import com.example.myapplication02.CREAT_MEMO_ROOT
import com.example.myapplication02.ui.memolist.MemoList
import com.example.myapplication02.SCREEN02_SCREEN_ROOT
import com.example.myapplication02.ui.admin.AdminUserList
import com.example.myapplication02.ui.login.UserViewModel
import com.example.myapplication02.ui.login.SignUpScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier, // This will be Modifier.padding(innerPadding) or Modifier.fillMaxSize()
    addMemoViewModel: AddMemoViewModel,
    userViewModel: UserViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = MAIN_SCREEN_ROOT,
        modifier = modifier // Apply the modifier (containing padding or fillMaxSize) to NavHost
    ) {
        composable(MAIN_SCREEN_ROOT) { // Will be correctly padded by NavHost
            MemoList(
                navController = navController,
                addMemoViewModel = addMemoViewModel
            )
        }

        composable(LOGIN_SCREEN_ROOT) { // Will be fillMaxSize by NavHost
            LogIn(
                userViewModel = userViewModel,
                navController = navController,
                // modifier for LogIn will be its default. LogIn then uses Scaffold(Modifier.fillMaxSize())
            )
        }

        composable(CREAT_MEMO_ROOT) {
            val id: Int = addMemoViewModel.isUpdate
            addMemoViewModel.updateItem(id)

            AddMemo( // Assuming Screen is defined in AddMemo.kt
                navController = navController,
                addMemoViewModel = addMemoViewModel,
                id = id
            )
        }

        composable(SCREEN02_SCREEN_ROOT) {
            ToDoList(
                navController = navController,
            )
        }

        composable(CREATE_USER_ROOT) {
            SignUpScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable(ADMIN_USER_LIST_ROOT) {
            AdminUserList(
                userViewModel = userViewModel,
                navController = navController,
            )
        }
    }
}