package com.farhanharis.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavUserViewModelFactory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object{
        @Volatile
        private var INSTANCE: FavUserViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavUserViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavUserViewModelFactory::class.java) {
                    INSTANCE = FavUserViewModelFactory(application)
                }
            }
            return INSTANCE as FavUserViewModelFactory
        }
    }


    @Suppress("UNCHECKED_CAST")
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavUserViewModel::class.java)) {
            return FavUserViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}