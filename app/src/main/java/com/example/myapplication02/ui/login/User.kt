package com.example.myapplication02.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.component1
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication02.MAIN_SCREEN_ROOT
import com.example.myapplication02.ui.theme.MyApplication02Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,// 실제 사용 시 ViewModel 주입
    id: Int = -1,
) {
    val isUpdateUser = userViewModel.isUpdateUser
    val listUsers = userViewModel.listUsers.collectAsState().value
    val listItems = userViewModel.listItems.collectAsState().value
    val currentUser = userViewModel.currentUser.collectAsState().value

    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    LaunchedEffect(id) {
        if (id != -1) {
//            val currentUser = listUsers.find { it.id == id }
            val currentLogin = listItems.find { it.id == id }
            userId = currentLogin?.userId.toString()
            password = currentLogin?.password.toString()
            name = currentUser?.name.toString()
            email = currentUser?.email.toString()
            phone = currentUser?.phone.toString()
            address = currentUser?.address.toString()
        } else {
            userId = ""
            password = ""
            name = ""
            email = ""
            phone = ""
            address = ""
        }
    }

//    if (isUpdateUser != -1) {
//        val currentUser = userViewModel.listUsers.collectAsState().value.find { it.id == isUpdateUser }
//        val currentLogin = userViewModel.listItems.collectAsState().value.find { it.id == isUpdateUser }
//
//        userId = currentLogin?.userId.toString()
//        password = currentLogin?.password.toString()
//        name = currentUser?.name.toString()
//        email = currentUser?.email.toString()
//        phone = currentUser?.phone.toString()
//        address = currentUser?.address.toString()
//    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("회원가입") })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                Text("로그인 정보", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = userId,
                    onValueChange = { userId = it },
                    label = { Text("아이디") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("비밀번호") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }

            item {
                Text("사용자 정보", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("이름") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("이메일") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("전화번호") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("주소") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            item {
                val scope = rememberCoroutineScope()
                println("id: $id")
                Button(
                    onClick = {
                        if (id == -1) {
                            scope.launch {
                                userViewModel.signUp(
                                    LogIn(userId = userId, password = password),
                                    User(name = name, email = email, phone = phone, address = address)
                                )
                            }

                        } else {
                            scope.launch {
                                val user = userViewModel.updateUser(
                                    LogIn(id = id, userId = userId, password = password),
                                    User(id = id, logInOwnerId = id, name = name, email = email, phone = phone, address = address)
                                )
                            }
                        }
                        // val idNew = if (id == -1) listItems.last().id + 1 else id
//                        println("idNew: $idNew")
                        // val user = User(id = idNew, logInOwnerId = idNew, name = name, email = email, phone = phone, address = address)

//                        println("user: $user")
                        navController.navigate(MAIN_SCREEN_ROOT)
                        val user = userViewModel.getUserByLoginId(name)
                        userViewModel.updateCurrentUser(user)
                        userViewModel.updateIsUpdateUser(-1)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    enabled = userId.isNotBlank() && password.isNotBlank() && name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && address.isNotBlank()
                ) {
                    Text("가입하기")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview() {
//    MyApplication02Theme {
//        SignUpScreen(rememberNavController(), UserViewModel())
//    }
//}
