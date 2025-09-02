package com.example.myapplication02.ui.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication02.ui.login.User
import com.example.myapplication02.ui.login.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun AdminUserList(
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val listLogins = userViewModel.listItems.collectAsState()
    val listUsers = userViewModel.listUsers.collectAsState()
    val listLoginIds = listLogins.value.map { it.userId }
    val listUserNames = listUsers.value.map { it.name }
    val inputUserName = remember { mutableStateOf("") }
    val searchingName = remember { mutableStateOf("") }
    val searchedUserId = remember { mutableStateListOf<User?>() }
    val isChecked = remember { mutableStateListOf<Boolean>() }
    for (i in 0 until listUsers.value.size) {
        isChecked.add(false)
    }

    Column {
        Text("유저 정보 리스트")

        Spacer(modifier = Modifier.height(10.dp))

        val scope = rememberCoroutineScope()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = inputUserName.value,
                onValueChange = { inputUserName.value = it },
                label = { Text("유저 이름 검색") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy( // Added keyboardOptions
                    imeAction = ImeAction.Done // Set Enter key action to "Done"
                ),
                keyboardActions = KeyboardActions( // Added keyboardActions
                    onDone = { // Handle "Done" action (Enter key press)
                        scope.launch {
                            // 여기에 엔터키 입력 시 수행할 작업을 정의합니다.
                            // 예: 할 일 목록에 추가하고 입력 필드 비우기
                            if (inputUserName.toString().isNotBlank()) { // 비어있지 않은 경우에만 처리
                                searchingName.value = inputUserName.value
                                val searchedUser = userViewModel.getUserByUserName(inputUserName.value)
                                searchedUserId.clear()
                                searchedUserId.addAll(searchedUser)
                            }
                        }
                    }
                )
            )

            if (isChecked.any { it }) {
                IconButton(
                    onClick = {
                        scope.launch {
                            listUsers.value.forEachIndexed { index, user ->
                                if (isChecked[index]) {
                                    userViewModel.deleteUser(user)
                                }
                            }
                            isChecked.clear()
                            for (i in 0 until listUsers.value.size) {
                                isChecked.add(false)
                            }
                        }
                    }
                ) {
                    Image(
                        Icons.Filled.Delete,
                        contentDescription = "Delete",
                    )
                }
            }

        }

//        Text("${toDoChecked.value}")

        if (listUsers.value.isEmpty() && searchedUserId.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes up remaining space
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("앱 망함 유저 없음")
            }
        }else if (searchedUserId.isNotEmpty()) {
            Text("${searchingName.value} 검색 결과")
            LazyColumn {
                items(searchedUserId.size) { index ->
                    Row {
                        Checkbox(
                            checked = isChecked[index],
                            onCheckedChange = {
                                isChecked[index] = !isChecked[index]
                            } // 클릭 시 상태 변경
                        )
                        Text(
                            searchedUserId[index]?.name ?: "",
                            // textDecoration = if (isChecked[index]) TextDecoration.LineThrough else TextDecoration.None
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                userViewModel.deleteUser(searchedUserId[index]!!)
                                isChecked.removeAt(index)
                            },
                        ) {
                            Image(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                            )
                        }
                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn {
                items(listUsers.value.size) { index ->
                    Row {
                        Checkbox(
                            checked = isChecked[index],
                            onCheckedChange = {
                                isChecked[index] = !isChecked[index]
                            } // 클릭 시 상태 변경
                        )
                        Text(
                            listUsers.value[index].name,
//                            textDecoration = if (isChecked[index]) TextDecoration.LineThrough else TextDecoration.None
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                userViewModel.deleteUser(listUsers.value[index])
                                isChecked.removeAt(index)
                            },
                        ) {
                            Image(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                            )
                        }
                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


