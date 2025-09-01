package com.example.myapplication02.ui.memolist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication02.CREAT_MEMO_ROOT

// CREAT_MEMO_ROOT는 AddMemo 화면의 경로로 가정합니다.
//  실제 경로 상수를 사용하세요. 예: const val CREAT_MEMO_ROOT = "add_memo"

@Composable
fun MemoList(
    AddMemoViewModel: AddMemoViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val listArticles = AddMemoViewModel.listItems.collectAsState()

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

        if (listArticles.value.isEmpty()) {
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
                items(listArticles.value.toList()) { (id, title, content) ->
                    Row {
                        MemoListItem(navController, AddMemoViewModel, id, title, content)
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
fun MemoListItem(
    navController: NavHostController,
    AddMemoViewModel: AddMemoViewModel,
    id: Int,
    title: String,
    content: String
) {
    val listItems = AddMemoViewModel.listItems.collectAsState()
    val title = listItems.value.find { it.id == id }?.title ?: ""
    val content = listItems.value.find { it.id == id }?.content ?: ""

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    modifier = Modifier.clickable {
                        AddMemoViewModel.updateItem(id)
                        navController.navigate(CREAT_MEMO_ROOT) // 실제 AddMemo 화면 경로로 변경,
                    },
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1, // 제목이 길 경우 한 줄로 표시 (필요시 조절)
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
                    AddMemoViewModel.removeMemoItem(Memo(id, title, content))
                }
            ) {
                Image(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }

    }
}