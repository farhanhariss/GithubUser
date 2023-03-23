package com.farhanharis.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

//    companion object {
//        private const val USERID
//    }

}