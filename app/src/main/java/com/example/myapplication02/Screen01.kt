package com.example.myapplication02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication02.ui.theme.MyApplication02Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("메롱바") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) { Text("+") }
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    "아메바",
                    modifier = Modifier.padding(16.dp)
                )
            }
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize(0.8f),
            contentColor = Color.Red
        ) {
            Greeting(
                name = "한글",
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, navController: NavHostController) {
    val image = painterResource(R.drawable.img01)
    val text = remember { mutableStateOf("123") }
    var textBy by remember { mutableStateOf("321") }
    val count = remember { mutableStateOf(1) }

    Column {
        Image(
            image,
            contentDescription = "dd"
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Hello $name!",
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .background(
                        shape = CircleShape,
                        color = Color(0xFFD0BCFF),
                    )
                    .padding(top = 10.dp)
                    .alpha(0.2f)
                    .size(width = 100.dp, height = 30.dp)
                    .blur(radius = 1.dp)
            )

            Button(onClick = {
                count.value++
                navController.navigate(MAIN_SCREEN_ROOT)
            }) {
                Text("홈으로 ${count.value}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = text.value,
            onValueChange = { NewText -> text.value = NewText }, // 입력 값 변경 시 상태 업데이트
            label = { Text("입력 ㅇㅇ") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = textBy,
            onValueChange = { NewTextBy -> textBy = NewTextBy }, // 입력 값 변경 시 상태 업데이트
            label = { Text("입력 ss") }
        )

    }
}