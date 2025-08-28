package com.example.myapplication02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar // Added import
import androidx.compose.material3.NavigationBarItem // Added import
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons // Added for default icons
import androidx.compose.material.icons.filled.Home // Example Icon
import androidx.compose.material.icons.filled.List // Example Icon
import androidx.compose.material.icons.filled.AccountCircle // Example Icon
import androidx.compose.material.icons.filled.Person // Example Icon for Login
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
// import androidx.compose.ui.tooling.preview.Preview // Preview might need adjustments
// import androidx.compose.ui.unit.dp // Not directly used in this snippet but often useful
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication02.navigation.AppNavigation
import com.example.myapplication02.ui.theme.MyApplication02Theme
import com.example.myapplication02.AddMemoViewModel

const val MAIN_SCREEN_ROOT = "home"
const val CREAT_MEMO_ROOT = "screen01"
const val SCREEN02_SCREEN_ROOT = "screen02"
const val LOGIN_SCREEN_ROOT = "login"

// Data class to represent navigation items
data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication02Theme {
                MyAppNavHost()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    var countMainButton by remember { mutableStateOf(1) }
    val text = remember { mutableStateOf("123") }
    val id = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showMainScaffold = currentRoute != LOGIN_SCREEN_ROOT

    // Define navigation items
    val navigationItems = listOf(
        BottomNavigationItem("Home", Icons.Filled.Home, MAIN_SCREEN_ROOT), // Added Home for completeness
        BottomNavigationItem("Screen01", Icons.Filled.List, CREAT_MEMO_ROOT),
        BottomNavigationItem("Screen02", Icons.Filled.AccountCircle, SCREEN02_SCREEN_ROOT),
        BottomNavigationItem("LogIn", Icons.Filled.Person, LOGIN_SCREEN_ROOT)
    )

    if (showMainScaffold) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = { TopAppBar(title = { Text("탑바") }) },
            bottomBar = {
                NavigationBar {
                    navigationItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
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
                        )
                    }
                }
            },
        ) { innerPadding ->
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                countMainButton = countMainButton,
                onIncrementCount = { countMainButton++ },
                text = text,
                id = id,
                password = password,
                onValueChange = { newText -> text.value = newText },
                addMemo = viewModel()
            )
        }
    } else {
        AppNavigation(
            navController = navController,
            modifier = modifier.fillMaxSize(),
            countMainButton = countMainButton,
            onIncrementCount = { countMainButton++ },
            text = text,
            id = id,
            password = password,
            onValueChange = { newText -> text.value = newText },
            addMemo = viewModel()
        )
    }
}

@Composable
fun GreetingMain(
    viewModel: CounterViewModel = viewModel(),
    liveModel: CounterLiveModel = viewModel(),
    name: String = "",
    id: String = "",
    password: String = "",
    modifier: Modifier = Modifier,
    navController: NavHostController,
    count: Int,
    onIncrementCount: () -> Unit
) {
    val viewModelCount = viewModel.count.collectAsState()
    val liveModelCount = liveModel.counter.observeAsState(0)

    Column(modifier = modifier) {
        Text(
            text = """
                |Hello $name!
                |your id : $id,
                |password : $password
            """.trimMargin()
        )

        Button(onClick = {
            onIncrementCount()
        }) {
            Text(count.toString())
        }

        Button(onClick = {
            viewModel.incrementCounter()
        }) {
            Text("ViewModel StateFlow 사용 카운트: ${viewModelCount.value}")
        }

        Button(onClick = {
            liveModel.incrementCounter()
        }) {
            Text("LiveData 사용 카운트: ${liveModelCount.value}")
        }
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
