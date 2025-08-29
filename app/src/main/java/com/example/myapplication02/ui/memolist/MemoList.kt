package com.example.myapplication02.ui.memolist

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication02.CREAT_MEMO_ROOT
import com.example.myapplication02.ui.theme.MyApplication02Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// CREAT_MEMO_ROOT는 AddMemo 화면의 경로로 가정합니다.
//  실제 경로 상수를 사용하세요. 예: const val CREAT_MEMO_ROOT = "add_memo"

@Composable
fun MemoList(
    text: AddMemoViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val listArticles by text.listArticles.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "메모 목록",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (listArticles.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes up remaining space
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("작성된 메모가 없습니다.")
                Spacer(modifier = Modifier.height(8.dp))
                Text("새로운 메모를 추가해보세요.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f), // Takes up available space before the button
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Space between items
            ) {
                items(listArticles.toList()) { (title, content) ->
                    Row {
                        MemoListItem(text, title = title, content = content)
//                        Spacer(modifier = Modifier.weight(1f))

                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(CREAT_MEMO_ROOT) // 실제 AddMemo 화면 경로로 변경
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("새 메모 작성하기")
        }
    }
}

@Composable
fun MemoListItem(text: AddMemoViewModel, title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1 // 제목이 길 경우 한 줄로 표시 (필요시 조절)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3 // 내용이 길 경우 3줄로 제한 (필요시 조절)
                )
            }
            IconButton(
                onClick = {
                    text.deleteArticle(title)
                }
            ) {
                Image(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }

    }
}

// Preview를 위한 Mock ViewModel
class PreviewAddMemoViewModel : AddMemoViewModel() {
    override val listArticles: StateFlow<Map<String, String>> =
        MutableStateFlow(
        mapOf(
            "미리보기 제목 1" to "미리보기 내용입니다. 첫 번째 메모입니다.",
            "미리보기 제목 2" to "두 번째 메모의 내용입니다. 잘 보이나요?"
        )
    )
    // 필요한 다른 메소드나 상태가 있다면 여기서 override 할 수 있습니다.
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun MemoListPreview() {
    MyApplication02Theme {
        MemoList(
            text = PreviewAddMemoViewModel(), // Mock ViewModel 사용
            navController = rememberNavController() // Preview용 NavController
        )
    }
}

@Preview(showBackground = true, name = "MemoList - Empty")
@Composable
fun MemoListEmptyPreview() {
    MyApplication02Theme {
        MemoList(
            text = object : AddMemoViewModel() { // 익명 객체로 빈 목록 Mock ViewModel
                override val listArticles: StateFlow<Map<String, String>> =
                    MutableStateFlow(emptyMap())
            },
            navController = rememberNavController()
        )
    }
}