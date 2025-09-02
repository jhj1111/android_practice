package com.example.myapplication02.ui.memolist

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity
data class Memo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String
)

@Database(entities = [Memo::class], version = 1)
abstract class AddMemoDatabase: RoomDatabase() {
    abstract fun addMemoDao(): AddMemoDao

    companion object {
        @Volatile
        private var INSTANCE: AddMemoDatabase? = null

        fun getInstance(context: Context): AddMemoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AddMemoDatabase::class.java,
                    "addmemo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}