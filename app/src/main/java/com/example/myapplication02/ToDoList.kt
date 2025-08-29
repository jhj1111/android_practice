package com.example.myapplication02

import androidx.compose.animation.core.copy
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun ToDoList(
    toDoListModel: ToDoListViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val inputToDo = toDoListModel.InputText.collectAsState()
    val listItems = toDoListModel.ListItems.collectAsState()
    val toDoChecked = toDoListModel.isChecked.collectAsState()

    Column {
        Text("오늘 할 일")
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = inputToDo.value,
            onValueChange = { newText -> toDoListModel.inputText(newText) },
            label = { Text("새로운 할 일")},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy( // Added keyboardOptions
                imeAction = ImeAction.Done // Set Enter key action to "Done"
            ),
            keyboardActions = KeyboardActions( // Added keyboardActions
                onDone = { // Handle "Done" action (Enter key press)
                    // 여기에 엔터키 입력 시 수행할 작업을 정의합니다.
                    // 예: 할 일 목록에 추가하고 입력 필드 비우기
                    if (inputToDo.value.isNotBlank()) { // 비어있지 않은 경우에만 처리
                        // toDoListModel.addToDoItem(inputToDo.value) // ViewModel에 할 일 추가 함수 호출 (가정)
                        toDoListModel.inputText("") // 입력 필드 비우기
                        toDoListModel.addToDoItem(inputToDo.value, false) // 리스트에 추가
                    }
                }
            )
        )
//        Text("${toDoChecked.value}")

        LazyColumn {
            items(listItems.value.size) { index ->
                if (listItems.value.isEmpty()) {
                    Text("할 일 목록이 없습니다.")
                } else {
                    Row {
                        Checkbox(
                            checked = toDoChecked.value[index],
                            onCheckedChange = {
                                toDoListModel.updateChecked(index)
                            } // 클릭 시 상태 변경
                        )
                        Text(
                            listItems.value[index],
                            textDecoration = if (toDoChecked.value[index]) TextDecoration.LineThrough else TextDecoration.None
                            )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                toDoListModel.removeToDoItem(listItems.value[index], index)
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