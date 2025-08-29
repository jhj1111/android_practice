package com.example.myapplication02.ui.memolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class AddMemoViewModel: ViewModel() {
    // private MutableStateFlow
    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")
    private val _listArticles = MutableStateFlow<Map<String, String>>(emptyMap())

    //public read-only StateFlow
    val title: StateFlow<String> get() = _title.asStateFlow()
    val content: StateFlow<String> get() = _content.asStateFlow()
    open val listArticles: StateFlow<Map<String, String>> get() = _listArticles.asStateFlow()


    fun setTitle(text: String) {
        // ViewModelScope를 사용하여 코루틴에서 상태 변경
        viewModelScope.launch { // Setter
            _title.value = text
        }
    }

    fun setContent(text: String) {
        // ViewModelScope를 사용하여 코루틴에서 상태 변경
        viewModelScope.launch { // Setter
            _content.value = text
        }
    }

    fun addArticle() { // 파라미터 제거
        val currentTitle = _title.value // 현재 ViewModel의 title 값
        val currentContent = _content.value // 현재 ViewModel의 content 값

        if (currentTitle.isNotBlank() && currentContent.isNotBlank()) {
            _listArticles.update { currentList ->
                currentList + (currentTitle to currentContent)
            }
            // 글 추가 후 입력 필드를 비우는 것은 Composable에서 처리하거나 여기서 할 수 있습니다.
            // _title.value = ""
            // _content.value = ""
        }
    }

    fun deleteArticle(title: String) {
        _listArticles.update { currentList ->
            currentList - title
        }
    }
}
