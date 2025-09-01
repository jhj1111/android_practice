package com.example.myapplication02.ui.memolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AddMemoDao {
    @Query("SELECT * FROM memo")
    fun getAll(): Flow<List<Memo>>

    @Query("SELECT * FROM memo WHERE id = :id")
    fun getById(id: Int): Flow<Memo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memo: Memo)

    @Update
    suspend fun update(memo: Memo)

    @Delete
    suspend fun delete(memo: Memo)
}