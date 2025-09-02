package com.example.myapplication02.ui.login

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LogInDao {
    @Query("SELECT * FROM login")
    fun getAll(): Flow<List<LogIn>>

    @Query("SELECT * FROM login WHERE id = :id")
    fun getById(id: Int): Flow<LogIn>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(login: LogIn): Long

    @Update
    suspend fun update(login: LogIn): Int

    @Delete
    suspend fun delete(login: LogIn)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Query
    ("SELECT * FROM user WHERE name LIKE '%' || :userName || '%'")
    suspend fun getByUserName(userName: String): List<User?>

    @Query("""
        SELECT u.* FROM user u 
        INNER JOIN login l ON u.logInOwnerId = l.id 
        WHERE l.userId = :userId
    """
    )
    suspend fun getByUserId(userId: String): User?

    @Query(
        """
        SELECT l.* FROM login l 
        INNER JOIN user u ON u.logInOwnerId = l.id 
        WHERE u.name = :userName
    """
    )
    suspend fun getLogInIdByUserId(userName: String): List<LogIn?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}