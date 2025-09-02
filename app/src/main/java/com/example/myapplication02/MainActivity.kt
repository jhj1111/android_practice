package com.example.myapplication02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar // Added import
import androidx.compose.material3.NavigationBarItem // Added import
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons // Added for default icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home // Example Icon
import androidx.compose.material.icons.filled.AccountCircle // Example Icon
import androidx.compose.material.icons.filled.Person // Example Icon for Login
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
// import androidx.compose.ui.tooling.preview.Preview // Preview might need adjustments
// import androidx.compose.ui.unit.dp // Not directly used in this snippet but often useful
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication02.navigation.AppNavigation
import com.example.myapplication02.ui.login.UserViewModel
import com.example.myapplication02.ui.theme.MyApplication02Theme

const val MAIN_SCREEN_ROOT = "Home"
const val CREAT_MEMO_ROOT = "screen01"
const val SCREEN02_SCREEN_ROOT = "ToDo"
const val LOGIN_SCREEN_ROOT = "Login"
const val CREATE_USER_ROOT = "SignUp"
const val ADMIN_USER_LIST_ROOT = "UserList"
const val USER_INFO_ROOT = "UserInfo"

// Data class to represent navigation items
data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String = label
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication02Theme {
                MyAppNavHost(userViewModel = viewModel())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    userViewModel: UserViewModel,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showMainScaffold = currentRoute != LOGIN_SCREEN_ROOT
    val currentUser = userViewModel.currentUser.collectAsState()
//    val user = id.value.ifBlank { "Guest" }
    val currentUserName = currentUser.value?.name ?: "Guest"

    // Define navigation items
    val navigationItems = listOf(
        BottomNavigationItem(MAIN_SCREEN_ROOT, Icons.Filled.Home), // Added Home for completeness
        BottomNavigationItem(CREAT_MEMO_ROOT, Icons.AutoMirrored.Filled.List),
        BottomNavigationItem(SCREEN02_SCREEN_ROOT, Icons.Filled.AccountCircle),
        if (currentUserName == "Guest") {
            BottomNavigationItem(LOGIN_SCREEN_ROOT, Icons.Filled.Person)
        } else {
            BottomNavigationItem(USER_INFO_ROOT, Icons.Filled.Person)
        }

    )

    if (showMainScaffold) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(currentUserName)
                            Row {
                                IconButton(onClick = {
                                    if (currentUserName=="Guest") {
                                        navController.navigate(LOGIN_SCREEN_ROOT)
                                    } else {
                                        userViewModel.logout()
                                        navController.popBackStack()
                                    }
                                }
                                )
                                {
                                    Image(if (currentUserName=="Guest") Icons.Filled.Person else Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Login")
                                }

                                if (currentUserName=="admin") {
                                    IconButton(onClick = {
                                        navController.navigate(ADMIN_USER_LIST_ROOT)
                                    }) {
                                        Image(Icons.AutoMirrored.Filled.List, contentDescription = "Login")
                                    }
                                }
                            }
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    navigationItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                if (item.route == MAIN_SCREEN_ROOT) {
                                    navController.popBackStack()
                                } else {
                                    navController.navigate(item.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            },
        ) { innerPadding ->
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                addMemoViewModel = viewModel(),
                userViewModel = userViewModel,
            )
        }
    } else {
        AppNavigation(
            navController = navController,
            modifier = modifier.fillMaxSize(),
            addMemoViewModel = viewModel(),
            userViewModel = userViewModel,
        )
    }
}

// Preview for GreetingMain might need adjustment if it relies on a specific Scaffold structure or innerPadding
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreviewMain() {
//    MyApplication02Theme {
//        GreetingMain(
//            name = "Android",
//            navController = rememberNavController(),
//            count = 1,
//            onIncrementCount = {}
//        )
//    }
//}
