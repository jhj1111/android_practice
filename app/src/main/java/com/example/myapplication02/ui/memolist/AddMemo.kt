package com.example.myapplication02.ui.memolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue // collectAsState already provides this
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication02.MAIN_SCREEN_ROOT
import kotlinx.coroutines.flow.MutableStateFlow

// Assuming MAIN_SCREEN_ROOT is defined elsewhere, e.g., in MainActivity.kt
// const val MAIN_SCREEN_ROOT = "home"

@Entity
data class Memo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String
)

@Composable
fun AddMemo(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    addMemoViewModel: AddMemoViewModel // ViewModel을 파라미터로 받도록 수정
) {
    val listItems = addMemoViewModel.listItems.collectAsState()
    val isUpdate = addMemoViewModel.isUpdate.collectAsState()
    val title = remember { mutableStateOf("") }
    val content = remember { mutableStateOf("") }

    // isUpdate 값이 변경될 때마다(수정 모드 진입/해제 시) 실행됩니다.
    LaunchedEffect(isUpdate) {
        if (isUpdate.value != -1) {
            // 수정 모드일 경우, ViewModel에서 해당 메모를 찾아 제목과 내용을 설정합니다.
            val memoToUpdate = listItems.value.find { it.id == isUpdate.value }
            title.value = memoToUpdate?.title ?: ""
            content.value = memoToUpdate?.content ?: ""
            addMemoViewModel.updateItem(-1)
        } else {
            // 새 메모 작성 모드일 경우, 필드를 비웁니다.
            title.value = ""
            content.value = ""
        }
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "새 메모 작성",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(isUpdate.value.toString())

        OutlinedTextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text("제목") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = content.value,
            onValueChange = { content.value = it },
            label = { Text("내용") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Takes up available vertical space
            minLines = 5 // Show at least 5 lines for content
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    title.value = ""
                    content.value = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("취소") // Changed "홈으로" to "취소" for better UX in a creation screen
            }

            Button(
                onClick = {
                    if (title.value.isNotBlank() && content.value.isNotBlank()) {
                        addMemoViewModel.addMemoItem(Memo(title = title.value, content = content.value), isUpdate.value) // title, content 인자 제거 (ViewModel 내부 값 사용)
                        // 성공적으로 추가 후 이전 화면으로 돌아가거나, 목록 화면으로 이동
//                        navController.popBackStack() // 이전 화면으로 돌아가기
                        navController.navigate(MAIN_SCREEN_ROOT) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = title.value.isNotBlank() && content.value.isNotBlank() // Enable button only if fields are not blank
            ) {
                Text("저장") // Changed "등록" to "저장"
            }
        }
    }
}
