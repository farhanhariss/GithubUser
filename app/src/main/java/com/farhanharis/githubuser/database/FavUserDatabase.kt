package com.farhanharis.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farhanharis.githubuser.remote.response.ItemsItem

@Database(entities = [ItemsItem::class], version = 1)
abstract class FavUserDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: FavUserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavUserDatabase {
            if (INSTANCE == null) {
                synchronized(FavUserDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavUserDatabase::class.java, "user_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavUserDatabase
        }
    }
}