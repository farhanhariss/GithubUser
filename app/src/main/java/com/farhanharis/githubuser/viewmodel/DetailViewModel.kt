package com.farhanharis.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.farhanharis.githubuser.remote.response.ItemsItem
import com.farhanharis.githubuser.repository.FavUserRepository

class DetailViewModel(application: Application) : ViewModel() {

    private val mUserRepository: FavUserRepository = FavUserRepository(application)

    fun getAllUser(): LiveData<List<ItemsItem>> = mUserRepository.getAllFavoriteUser()

    fun insert(favUser: ItemsItem){
        mUserRepository.insertFavoriteUser(favUser)
    }
    fun delete(favUser: ItemsItem){
        mUserRepository.deleteFavoriteUser(favUser)
    }

}