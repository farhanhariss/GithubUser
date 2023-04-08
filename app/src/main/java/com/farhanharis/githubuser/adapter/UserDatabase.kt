package com.farhanharis.githubuser.adapter

import android.content.Context
import android.service.autofill.UserData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farhanharis.githubuser.database.UserDao
import com.farhanharis.githubuser.remote.response.ItemsItem

@Database(entities = [ItemsItem::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao():UserDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java, "News.db"
                ).build()
            }
    }
}