package com.farhanharis.githubuser.remote.network

import android.provider.Contacts.SettingsColumns.KEY
import com.farhanharis.githubuser.remote.GithubResponse
import com.farhanharis.githubuser.remote.ItemsItem
import com.farhanharis.githubuser.remote.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //Search User
    @GET ("search/users")
    fun getListUserData(
        @Query ("q") query: String
    ) : Call<GithubResponse>

    //Data User
    @GET ("user/{username}")
    fun getDetailUserData(
        @Path("username") username : String
    ) : Call<UserResponse>

    // Get Followers
    @GET ("user/{username}/following")
    fun getDataFollowers(
        @Path("username") username : String
    ) : Call<List<ItemsItem>>

    //Get Following
    @GET("user/{username/followers}")
    fun getDataFollowing(
        @Path("username") username : String
    ) : Call<List<ItemsItem>>
}