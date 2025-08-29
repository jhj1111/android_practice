package com.example.myapplication02.ui.memolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
// import androidx.compose.foundation.Image // Not used in this proposal
// import androidx.compose.foundation.background // Not used
// import androidx.compose.foundation.layout.size // Not used
// import androidx.compose.foundation.shape.CircleShape // Not used
// import androidx.compose.foundation.text.KeyboardActions // Can be added if specific actions are needed
// import androidx.compose.foundation.text.KeyboardOptions // Can be added for specific input types
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue // collectAsState already provides this
// import androidx.compose.runtime.mutableStateOf // ViewModel handles state
// import androidx.compose.runtime.remember // ViewModel handles state
// import androidx.compose.runtime.setValue // ViewModel handles state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import androidx.compose.ui.draw.alpha // Not used
// import androidx.compose.ui.draw.blur // Not used
// import androidx.compose.ui.graphics.Color // Not used in this proposal
// import androidx.compose.ui.res.painterResource // Not used
// import androidx.compose.ui.text.input.ImeAction // Can be added
// import androidx.compose.ui.text.font.FontStyle // Not used
import androidx.compose.ui.unit.dp
// import androidx.compose.ui.unit.sp // Use MaterialTheme typography
// import androidx.lifecycle.viewmodel.compose.viewModel // ViewModel should be passed as a parameter
import androidx.navigation.NavHostController
import com.example.myapplication02.MAIN_SCREEN_ROOT

// Assuming MAIN_SCREEN_ROOT is defined elsewhere, e.g., in MainActivity.kt
// const val MAIN_SCREEN_ROOT = "home"

@Composable
fun AddMemo(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    addMemoViewModel: AddMemoViewModel // ViewModel을 파라미터로 받도록 수정
) {
    val title by addMemoViewModel.title.collectAsState()
    val content by addMemoViewModel.content.collectAsState()

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

        OutlinedTextField(
            value = title,
            onValueChange = { newText -> addMemoViewModel.setTitle(newText) },
            label = { Text("제목") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { newText -> addMemoViewModel.setContent(newText) },
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
                    navController.navigate(MAIN_SCREEN_ROOT) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("취소") // Changed "홈으로" to "취소" for better UX in a creation screen
            }

            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        addMemoViewModel.addArticle() // title, content 인자 제거 (ViewModel 내부 값 사용)
                        // 입력 필드 초기화는 addArticle 함수 내에서 처리하거나, 여기서 직접 호출
                        addMemoViewModel.setTitle("")
                        addMemoViewModel.setContent("")
                        // 성공적으로 추가 후 이전 화면으로 돌아가거나, 목록 화면으로 이동
                        navController.popBackStack() // 이전 화면으로 돌아가기
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = title.isNotBlank() && content.isNotBlank() // Enable button only if fields are not blank
            ) {
                Text("저장") // Changed "등록" to "저장"
            }
        }
    }
}
