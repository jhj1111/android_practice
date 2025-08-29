package com.example.myapplication02.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class ToDoListViewModel: ViewModel() {
    // private MutableStateFlow
    private val _inputText = MutableStateFlow("")
    private val _listItems = MutableStateFlow<List<String>>(emptyList<String>())
    private val _isChecked = MutableStateFlow<List<Boolean>>(emptyList<Boolean>())
    //public read-only StateFlow
    open val InputText: StateFlow<String> get() = _inputText.asStateFlow()
    open val ListItems: StateFlow<List<String>> get() = _listItems.asStateFlow()
    open val isChecked: StateFlow<List<Boolean>> get() = _isChecked.asStateFlow()

    open fun inputText(text: String) {
        viewModelScope.launch { // Setter
            _inputText.value = text
        }
    }

    open fun addToDoItem(item: String, checked: Boolean) {
        viewModelScope.launch {
            _listItems.update { currentList ->
                currentList + item
            }
            _isChecked.update { currentList ->
                currentList + checked
            }
        }
    }

    open fun removeToDoItem(item: String, index: Int) {
        viewModelScope.launch {
            _listItems.update { currentList ->
                currentList - item
            }
            _isChecked.update { currentChecked ->
                currentChecked.toMutableList().also {
                    it.removeAt(index)
                }
            }
        }
    }

    open fun updateChecked(index: Int) {
        viewModelScope.launch {
            _isChecked.update { currentList ->
                currentList.toMutableList().also {
                    it[index] = !it[index]
                }
            }
        }
    }
}