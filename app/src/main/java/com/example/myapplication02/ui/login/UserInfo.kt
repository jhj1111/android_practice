package com.example.myapplication02.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication02.CREATE_USER_ROOT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfo(
    navController: NavHostController,
    userViewModel: UserViewModel,
) {
    val currentUser = userViewModel.currentUser.collectAsState()
    var userLoginInfo by remember { mutableStateOf<LogIn?>(null) }
    var userInfo by remember { mutableStateOf<User?>(null) }
    LaunchedEffect(currentUser.value) {
        userLoginInfo = userViewModel.getLogInIdByUserName(currentUser.value?.name ?: "")
        userInfo = userViewModel.getUserByLoginId(userLoginInfo?.userId ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("개인 정보 털린다") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            Text(currentUser.toString())
            Text("로그인 정보")
            Text("아이디: ${userLoginInfo?.userId}")
            Text("비밀번호: ${userLoginInfo?.password}")
            Spacer(modifier = Modifier.height(16.dp))

            Text("사용자 정보")
            Text("이름: ${userInfo?.name}")
            Text("이메일: ${userInfo?.email}")
            Text("전화번호: ${userInfo?.phone}")
            Text("주소: ${userInfo?.address}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val id = currentUser.value?.id ?: -1
                userViewModel.updateIsUpdateUser(id)
                navController.navigate(CREATE_USER_ROOT)
            }) {
                Text("회원 정보 수정")
            }
        }
    }
}