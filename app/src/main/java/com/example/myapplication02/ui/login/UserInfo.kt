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
import androidx.compose.runtime.collectAsState
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
    val UserLoginInfo = userViewModel.listItems.collectAsState().value.find { it.id == currentUser.value?.id }
    val UserInfo = userViewModel.listUsers.collectAsState().value.find { it.id == currentUser.value?.id }

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
            Text("아이디: ${UserLoginInfo?.userId}")
            Text("비밀번호: ${UserLoginInfo?.password}")
            Spacer(modifier = Modifier.height(16.dp))

            Text("사용자 정보")
            Text("이름: ${UserInfo?.name}")
            Text("이메일: ${UserInfo?.email}")
            Text("전화번호: ${UserInfo?.phone}")
            Text("주소: ${UserInfo?.address}")
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