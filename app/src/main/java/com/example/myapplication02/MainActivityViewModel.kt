package com.example.myapplication02

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CounterViewModel: ViewModel() {
    // private MutableStateFlow
    private val _counter = MutableStateFlow(0)
    //public read-only StateFlow
    val count: StateFlow<Int> = _counter.asStateFlow()

    fun incrementCounter() {
        // ViewModelScope를 사용하여 코루틴에서 상태 변경
        viewModelScope.launch { // Setter
            _counter.value++
        }
    }
}