package com.example.myapplication02

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val count: StateFlow<Int> get() = _counter.asStateFlow()

    fun incrementCounter() {
        // ViewModelScope를 사용하여 코루틴에서 상태 변경
        viewModelScope.launch { // Setter
            _counter.value++
        }
    }
}

class CounterLiveModel: ViewModel() {
    private val _counter = MutableLiveData(0)
    val counter: LiveData<Int> get() = _counter

    fun incrementCounter() {
        _counter.value = (_counter.value ?: 0) + 1
    }
}