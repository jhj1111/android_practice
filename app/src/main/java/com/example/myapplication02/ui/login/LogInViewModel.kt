package com.example.myapplication02.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class LogInViewModel: ViewModel() {
    // private MutableStateFlow
    private val _id = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    //public read-only StateFlow
    val id: StateFlow<String> get() = _id.asStateFlow()
    val password: StateFlow<String> get() = _password.asStateFlow()

    fun updateId(newId: String) {
        viewModelScope.launch {
            _id.value = newId
        }
    }

    fun updatePassword(newPassword: String) {
        viewModelScope.launch {
            _password.value = newPassword
        }
    }
}
