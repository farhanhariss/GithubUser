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
    @Headers("Authorization: $KEY")
    @GET ("search/users")
    fun getSearchData(
        @Query ("q") query: String
    ) : Call<GithubResponse>

    //Data User
    @Headers("Authorization : $KEY")
    @GET ("user/{username}")
    fun getUserData(
        @Path("username") username : String
    ) : Call<UserResponse>

    // Get Followers
    @Headers("Authorization : $KEY")
    @GET ("user/{username}/following")
    fun getDataFollowers(
        @Path("username") username : String
    ) : Call<List<ItemsItem>>

    //Get Following
    @Headers("Authorization : $KEY")
    @GET("user/{username/followers}")
    fun getDataFollowing(
        @Path("username") username : String
    ) : Call<List<ItemsItem>>
}