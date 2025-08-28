package com.example.myapplication02

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun MemoList(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    memoList: AddMemoViewModel = viewModel()
) {
    val listArticles = memoList.listArticles.collectAsState()

    Column {
        Text("메모 리스트")
        Text(listArticles.value.size.toString())
        LazyColumn(modifier = Modifier.height(200.dp)) {

//            items(listArticles.value.size) { index ->
//                val title = listArticles.value.keys.elementAt(index)
//                val content = listArticles.value.values.elementAt(index)
//                Text(title)
//                Text(content)
//            }

            items(listArticles.value.toList()) { (title, content) ->
                Text(title)
                Text(content)
            }
        }


        Button(
            onClick = {
                navController.navigate(CREAT_MEMO_ROOT)
            },
        ) { Text("메모 작성하기") }
    }

    }