package com.farhanharis.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.farhanharis.githubuser.database.FavUserDatabase
import com.farhanharis.githubuser.database.UserDao
import com.farhanharis.githubuser.remote.response.ItemsItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val mfavUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserDatabase.getDatabase(application)
        mfavUserDao = db.userDao()
    }

    fun getAllFavoriteUser(): LiveData<List<ItemsItem>> {
        return mfavUserDao.getAllFavoriteUser()
    }

    fun insertFavoriteUser(favUser : ItemsItem){
        return executorService.execute{mfavUserDao.insertFavoriteUser(favUser)}
    }

    fun deleteFavoriteUser(favUser: ItemsItem){
        return executorService.execute{mfavUserDao.deleteFavoriteUser(favUser)}
    }
}
