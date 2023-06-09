package com.farhanharis.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farhanharis.githubuser.remote.response.DetailUserResponse
import com.farhanharis.githubuser.remote.response.GithubResponse
import com.farhanharis.githubuser.remote.response.ItemsItem
import com.farhanharis.githubuser.remote.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel :ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _detailDataUsers = MutableLiveData<DetailUserResponse>()
    val detailDataUser : LiveData<DetailUserResponse> = _detailDataUsers

    private val _detailUsersFollowers = MutableLiveData<List<ItemsItem>>()
    val detailUsersFollowers : LiveData<List<ItemsItem>> = _detailUsersFollowers

    private val _detailUsersFollowing = MutableLiveData<List<ItemsItem>>()
    val detailUsersFollowing : LiveData<List<ItemsItem>> = _detailUsersFollowing

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun githubFindUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUserData(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                Log.e(TAG, "OnFailure : ${t.message}")
            }

        })
    }

    fun githubGetDetailUser(username : String){
         _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUserData(username)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailDataUsers.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure : ${t.message}")
            }
        })
    }

    fun githubGetFollowers(username : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDataFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _detailUsersFollowers.value = response.body()
                }else{
                    Log.e(TAG, "OnFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure : ${t.message}")
            }
        })
    }

    fun githubGetFollowing(username : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDataFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _detailUsersFollowing.value = response.body()
                }else{
                    Log.e(TAG, "OnFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "OnFailure : ${t.message}")
            }
        })
    }
}
