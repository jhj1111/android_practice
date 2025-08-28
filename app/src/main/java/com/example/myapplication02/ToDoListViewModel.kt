package com.example.myapplication02

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ToDoListViewModel: ViewModel() {
    // private MutableStateFlow
    private val _inputText = MutableStateFlow("")
    private val _listItems = MutableStateFlow<List<String>>(emptyList<String>())
    private val _isChecked = MutableStateFlow<List<Boolean>>(emptyList<Boolean>())
    //public read-only StateFlow
    val InputText: StateFlow<String> get() = _inputText.asStateFlow()
    val ListItems: StateFlow<List<String>> get() = _listItems.asStateFlow()
    val isChecked: StateFlow<List<Boolean>> get() = _isChecked.asStateFlow()

    fun inputText(text: String) {
        viewModelScope.launch { // Setter
            _inputText.value = text
        }
    }



    fun addToDoItem(item: String) {
        viewModelScope.launch {
            _listItems.update { currentList ->
                currentList + item
            }
        }
    }

    fun removeToDoItem(item: String) {
        viewModelScope.launch {
            _listItems.update { currentList ->
                currentList - item
            }
        }
    }

    fun addChecked() {
        // ViewModelScope를 사용하여 코루틴에서 상태 변경
        viewModelScope.launch { // Setter
            _isChecked.update { currentList ->
                currentList + false
            }
        }
    }

//    fun updateChecked(index: Int) {
//        // ViewModelScope를 사용하여 코루틴에서 상태 변경
//        viewModelScope.launch { // Setter
//            _isChecked.update { currentList ->
//                if (_isChecked.value[index]==true) {
//                    currentList.elementAt(index) = false
//                } else {
//                    currentList[index] = true
//                }
//                currentList
//            }
//        }
//    }
}