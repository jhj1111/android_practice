package com.example.myapplication02.ui.todolist

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication02.ui.theme.MyApplication02Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.emptyList

@Entity(tableName = "todo")
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    var isChecked: Boolean = false
)

@Composable
fun ToDoList(
    toDoListModel: ToDoListViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val listItems = toDoListModel.listItems.collectAsState()
    val inputToDo = listItems.value.map { it.title }
    val isChecked = listItems.value.map { it.isChecked }
    val inputTitle = remember { mutableStateOf("") }

    Column {
        Text("오늘 할 일")
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = inputTitle.value,
            onValueChange = { inputTitle.value = it },
            label = { Text("새로운 할 일")},
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy( // Added keyboardOptions
                imeAction = ImeAction.Done // Set Enter key action to "Done"
            ),
            keyboardActions = KeyboardActions( // Added keyboardActions
                onDone = { // Handle "Done" action (Enter key press)
                    // 여기에 엔터키 입력 시 수행할 작업을 정의합니다.
                    // 예: 할 일 목록에 추가하고 입력 필드 비우기
                    if (inputToDo.toString().isNotBlank()) { // 비어있지 않은 경우에만 처리
                        toDoListModel.addToDoItem(ToDo(title = inputTitle.value))
                        inputTitle.value = ""
                    }
                }
            )
        )
//        Text("${toDoChecked.value}")

        if (listItems.value.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes up remaining space
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("할 일 없음?")
            }
        } else {
            LazyColumn {
                items(listItems.value.size) { index ->


                    Row {
                        Checkbox(
                            checked = isChecked[index],
                            onCheckedChange = {
                                toDoListModel.toggleChecked(listItems.value[index])
                            } // 클릭 시 상태 변경
                        )
                        Text(
                            inputToDo[index],
                            textDecoration = if (isChecked[index]) TextDecoration.LineThrough else TextDecoration.None
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                toDoListModel.removeToDoItem(listItems.value[index])
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


