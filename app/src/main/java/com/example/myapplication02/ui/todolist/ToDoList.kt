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
import com.example.myapplication02.ui.theme.MyApplication02Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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




// Preview를 위한 Mock ViewModel
class PreviewToDoListViewModel : ToDoListViewModel() {
    private val _previewInputText = MutableStateFlow("미리보기 할 일 입력")
    override val InputText: StateFlow<String> = _previewInputText.asStateFlow()

    private val _previewListItems = MutableStateFlow(listOf("장보기", "스터디하기", "운동하기"))
    override val ListItems: StateFlow<List<String>> = _previewListItems.asStateFlow()

    private val _previewIsChecked = MutableStateFlow(listOf(false, true, false))
    override val isChecked: StateFlow<List<Boolean>> = _previewIsChecked.asStateFlow()

    // Mock implementations for functions if they are called directly or affect state observed in Preview
    override fun inputText(text: String) {
        _previewInputText.value = text
    }

    override fun addToDoItem(item: String, checked: Boolean) {
        _previewListItems.value = _previewListItems.value + item
        _previewIsChecked.value = _previewIsChecked.value + checked
    }

    override fun removeToDoItem(item: String, index: Int) {
        _previewListItems.value = _previewListItems.value.toMutableList().also { it.removeAt(index) }
        _previewIsChecked.value = _previewIsChecked.value.toMutableList().also { it.removeAt(index) }
    }

    override fun updateChecked(index: Int) {
        _previewIsChecked.value = _previewIsChecked.value.toMutableList().also {
            it[index] = !it[index]
        }
    }
    // count StateFlow가 있다면 여기서 Mock 데이터 제공 (이전 제안에서는 주석 처리되어 있었음)
    // override val count: StateFlow<Int> = MutableStateFlow(3)
}

//@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ToDoListPreview() {
    MyApplication02Theme {
        ToDoList(
            toDoListModel = PreviewToDoListViewModel(),
            navController = rememberNavController()
        )
    }
}