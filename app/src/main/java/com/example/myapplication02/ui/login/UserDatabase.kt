package com.example.myapplication02.ui.login

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * LogIn 테이블: 로그인 계정 정보
 * id가 자동 생성되는 기본 키(Primary Key) 역할을 합니다.
 */
@Entity(tableName = "login")
data class LogIn(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val password: String
)

/**
 * User 테이블: 사용자 상세 정보
 * - id: User 테이블 자체의 고유한 기본 키입니다.
 * - logInOwnerId: LogIn 테이블의 id를 참조하는 외래 키(Foreign Key)입니다.
 */
@Entity(
    tableName = "user",
    foreignKeys = [
        ForeignKey(
            entity = LogIn::class,
            parentColumns = ["id"], // 부모(LogIn)의 기본 키 컬럼
            childColumns = ["logInOwnerId"], // 자식(User)의 외래 키 컬럼
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // 이 User의 고유 ID
    val logInOwnerId: Int = id, // 이 User를 소유한 LogIn 계정의 ID
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val created_at: String,
)

// 데이터베이스 버전 번호를 2로 올립니다. (스키마가 변경되었기 때문)
@Database(entities = [LogIn::class, User::class], version = 2)
abstract class UserDatabase : RoomDatabase() {
    abstract fun logInDao(): LogInDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                )
                // 스키마가 변경되었으므로, 기존 데이터베이스를 삭제하고 새로 생성하도록 설정합니다.
                // 개발 중에는 이 방법이 가장 간단합니다.
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
