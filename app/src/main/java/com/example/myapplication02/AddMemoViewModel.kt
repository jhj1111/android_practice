package com.example.myapplication02

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddMemoViewModel: ViewModel() {
    // private MutableStateFlow
    private val _title = MutableStateFlow("")
    private val _content = MutableStateFlow("")
    private val _listArticles = MutableStateFlow<Map<String, String>>(emptyMap())

    //public read-only StateFlow
    val title: StateFlow<String> get() = _title.asStateFlow()
    val content: StateFlow<String> get() = _content.asStateFlow()
    val listArticles: StateFlow<Map<String, String>> get() = _listArticles.asStateFlow()


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

    fun addArticle(title: String, content: String) {
        // ViewModelScope를 사용하여 코루틴에서 상태 변경
        viewModelScope.launch { // Setter
            _listArticles.update { currentList ->
                currentList + (title to content)
            }
        }
    }


}

