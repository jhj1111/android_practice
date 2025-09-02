package com.example.myapplication02.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private var _logInDao = UserDatabase.getInstance(application).logInDao()
    private var _userDao = UserDatabase.getInstance(application).userDao()
    private val _listItems = MutableStateFlow<List<LogIn>>(emptyList<LogIn>())
    private val _listUsers = MutableStateFlow<List<User>>(emptyList<User>())
    private val _currentUser = MutableStateFlow<User?>(null)

    val listItems: StateFlow<List<LogIn>> get() = _listItems.asStateFlow()
    val listUsers: StateFlow<List<User>> get() = _listUsers.asStateFlow()
    val currentUser: StateFlow<User?> get() = _currentUser.asStateFlow()

    init {
        viewModelScope.launch {
            _logInDao.getAll().collect { list ->
                _listItems.value = list
            }
            _userDao.getAll().collect { list ->
                _listUsers.value = list
            }
        }
    }

    fun isValidUserId(userId: String): Boolean = listItems.value.none { it.userId == userId }

    fun isValidUser(userId: String, password: String): Boolean =
        listItems.value.any { it.userId == userId && it.password == password }

    suspend fun getUserByLoginId(loginId: String): User? =
        _userDao.getByUserId(loginId)

    fun updateCurrentUser(user: User?) {
        viewModelScope.launch {
            _currentUser.value = user
        }
    }

    fun logout() = updateCurrentUser(null)

    fun insertIdPassword(logIn: LogIn) {
        viewModelScope.launch {
            _logInDao.insert(logIn)
        }
    }

    fun updateIdPassword(logIn: LogIn) {
        if(!isValidUserId(logIn.userId)) return

        viewModelScope.launch {
            _logInDao.update(logIn)
        }
    }

    fun deleteIdPassword(logIn: LogIn) {
        viewModelScope.launch {
            _logInDao.delete(logIn)
        }
    }

    fun signUp(login: LogIn, user: User) {
        if(!isValidUserId(login.userId)) return

        viewModelScope.launch {
            // 1. login 정보를 삽입하고, 자동으로 생성된 primary key를 가져옴
            val createdId = _logInDao.insert(login)
            // 2. 반환받은 ID를 user 객체의 외래 키 필드에 설정하여 새로운 user 객체를 생성합니다.
            //    (user.copy를 사용, user의 외래 키 필드명은 실제 프로젝트에 맞게 수정해야 합니다. ex: logInId)
            val userWithLoginInfo = user.copy(logInOwnerId = createdId.toInt())
            _userDao.insert(userWithLoginInfo)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            _userDao.update(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            _userDao.delete(user)
        }
    }
}

open class LogInViewModel1: ViewModel() {
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

    fun logout() {
        viewModelScope.launch {
            _id.value = ""
            _password.value = ""
        }
    }
}
