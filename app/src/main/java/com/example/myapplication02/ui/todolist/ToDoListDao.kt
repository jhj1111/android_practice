package com.example.myapplication02.ui.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoListDao {
    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getById(id: Int): Flow<ToDo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: ToDo)

    @Update
    suspend fun update(todo: ToDo)

    @Delete
    suspend fun delete(todo: ToDo)

}