package com.example.myapplication02

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun AddMemo(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    addMemo: AddMemoViewModel = viewModel(),
) {
    val title = addMemo.title.collectAsState()
    val content = addMemo.content.collectAsState()

    Column {
        OutlinedTextField(
            value = title.value,
            onValueChange = { newText -> addMemo.setTitle(newText) },
            label = { Text("title") },
            singleLine = true,
        )

        OutlinedTextField(
            value = content.value,
            onValueChange = { newText -> addMemo.setContent(newText) },
            label = { Text("content") },
        )

        Row {
            Button(
                onClick = {
                    if (title.value.isNotBlank() && content.value.isNotBlank()) {
                        addMemo.setTitle(title.value)
                        addMemo.setContent(content.value)
                        addMemo.addArticle(title.value, content.value)
                        addMemo.setTitle("")
                        addMemo.setContent("")
                    }
                },
            ) { Text("등록") }

            Button(
                onClick = {
                    navController.navigate(MAIN_SCREEN_ROOT)
                },
            ) { Text("홈으로") }
        }

    }
}