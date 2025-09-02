package com.example.myapplication02.ui.memolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddMemoViewModel(application: Application) : AndroidViewModel(application) {
    private var _addMemoDao = AddMemoDatabase.getInstance(application).addMemoDao()
    private var _isUpdate = -1
    private val _listItems = MutableStateFlow<List<Memo>>(emptyList<Memo>())

    val listItems: StateFlow<List<Memo>> get() = _listItems.asStateFlow()
    val isUpdate: Int get() = _isUpdate

    init {
        viewModelScope.launch {
            _addMemoDao.getAll().collect { list ->
                _listItems.value = list
            }
        }
    }

    fun addMemoItem(item: Memo, id: Int = -1) {
        viewModelScope.launch {
            when (id) {
                -1 -> _addMemoDao.insert(item)
                else -> {
//                    val memo = listItems.value.find { it.id == id } ?: return@launch
                    _addMemoDao.update(Memo(id = id, item.title, item.content))
                }
            }
//            _addMemoDao.insert(item)
        }
    }

//    fun removeMemoItem(item: Memo) {
//        viewModelScope.launch {
//            _addMemoDao.delete(item)
//        }
//    }

//    fun removeMemoItem(title: String) {
//        val memo = (listItems as Memo).copy(title = title)
//        viewModelScope.launch {
//
//            _addMemoDao.delete(memo)
//        }
//    }

//    fun removeMemoItem(content: String) {
//        viewModelScope.launch {
//            val memo = (listItems as Memo).copy(content = content)
//
//            _addMemoDao.delete(memo)
//        }
//    }

    fun removeMemoItem(item: Any) {
        viewModelScope.launch {
            val memo: Memo = when(item) {
                is String -> (listItems as Memo).copy(title = item)
                is Memo -> item
                else -> throw IllegalArgumentException("Unsupported item type")
            }
            _addMemoDao.delete(memo)
        }
    }

    fun updateItem(id: Int) {
//        _isUpdate = if (_isUpdate == -1) id else -1
        _isUpdate = id
    }
}

open class AddMemoViewModel1: ViewModel() {
    // private MutableStateFlow
    private val _title = MutableStateFlow("")
    private val _preTitle = MutableStateFlow("")
    private val _content = MutableStateFlow("")
    private val _listArticles = MutableStateFlow<Map<String, String>>(emptyMap())

    //public read-only StateFlow
    val title: StateFlow<String> get() = _title.asStateFlow()
    val preTitle: StateFlow<String> get() = _preTitle.asStateFlow()
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

    fun clearTitleContent() {
        _title.value = ""
        _preTitle.value = ""
        _content.value = ""
    }

    fun addArticle() { // 파라미터 제거
        val currentTitle = _title.value // 현재 ViewModel의 title 값
        val preTitle = _preTitle.value
        val currentContent = _content.value // 현재 ViewModel의 content 값

        _listArticles.update { currentList ->
            var listAfterDelect = currentList
            if (preTitle != "") {
                listAfterDelect = currentList - preTitle
            }
            listAfterDelect + (currentTitle to currentContent)
        }
        // 글 추가 후 입력 필드를 비우는 것은 Composable에서 처리하거나 여기서 할 수 있습니다.
        _title.value = ""
        _preTitle.value = ""
        _content.value = ""
        }

    fun deleteArticle(title: String) {
        _listArticles.update { currentList ->
            currentList - title
        }
    }

    fun updateArticle(title: String, content: String) {
        _title.value = title
        _preTitle.value = title
        _content.value = content
    }
}
