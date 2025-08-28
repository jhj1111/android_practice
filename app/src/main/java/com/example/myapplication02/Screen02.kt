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
import androidx.compose.runtime.MutableState
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication02.ui.theme.MyApplication02Theme

@Composable
fun Greeting02(
    name: String,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    value: MutableState<String> = remember { mutableStateOf("") },
    onValueChange: (String) -> Unit = {},
) {
    val image = painterResource(R.drawable.cat05)


    Column {
        Spacer(modifier = Modifier.height(200.dp))

        Image(
            image,
            contentDescription = "dd",
            modifier = Modifier
                .size(200.dp)
                .padding(50.dp)
        )
        TextField(
            value = value.value,
            onValueChange = onValueChange, // 입력 값 변경 시 상태 업데이트
            label = { Text("Screen02") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Button(onClick = { navController.popBackStack() }) {
                Text("뒤로")
            }

            Button(onClick = { navController.navigate("$MAIN_SCREEN_ROOT/${value.value}") }) {
                Text("홈으로 데이터 전송")
            }
        }
    }




}