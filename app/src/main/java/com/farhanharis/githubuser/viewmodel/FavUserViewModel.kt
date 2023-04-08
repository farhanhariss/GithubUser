package com.farhanharis.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.farhanharis.githubuser.remote.response.ItemsItem
import com.farhanharis.githubuser.repository.FavUserRepository

class FavUserViewModel(application: Application) :ViewModel() {

    private val mFavUserRepository : FavUserRepository = FavUserRepository(application)

    fun getAllUserFavorite():LiveData<List<ItemsItem>> = mFavUserRepository.getAllFavoriteUser()
}