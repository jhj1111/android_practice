package com.example.myapplication02.ui.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ToDoListViewModel(application: Application) : AndroidViewModel(application) {
    private var _toDoDao = ToDoDatabase.getInstance(application).todoDao()

    private val _listItems = MutableStateFlow<List<ToDo>>(emptyList<ToDo>())

    val listItems: StateFlow<List<ToDo>> get() = _listItems.asStateFlow()

    init {
        viewModelScope.launch {
            _toDoDao.getAll().collect { list ->
                _listItems.value = list
            }
        }
    }

    fun addToDoItem(item: ToDo = ToDo(title = "test")) {
        viewModelScope.launch {
            _toDoDao.insert(item)
        }
    }

    fun removeToDoItem(item: ToDo) {
        viewModelScope.launch {
            _toDoDao.delete(item)
        }
    }

    fun updateItem(item: ToDo = ToDo(title = "test")) {
        viewModelScope.launch {
            _toDoDao.update(item)
        }
    }

    fun updateChecked(item: ToDo) {
        viewModelScope.launch {
            item.isChecked = !item.isChecked
            _toDoDao.update(item)
        }
    }

    fun toggleChecked(item: ToDo) {
        viewModelScope.launch {
            val checkedState = item.copy(isChecked = !item.isChecked)
            _toDoDao.update(checkedState)
        }
    }
}

open class ToDoListViewModel1: ViewModel() {
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