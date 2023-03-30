package com.farhanharis.githubuser.remote.network

import com.farhanharis.githubuser.remote.response.DetailUserResponse
import com.farhanharis.githubuser.remote.response.GithubResponse
import com.farhanharis.githubuser.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //Search User
    @GET ("search/users")
    fun getListUserData(
        @Query ("q") query: String
    ) : Call<GithubResponse>

    //Data User
    @GET ("users/{username}")
    fun getDetailUserData(
        @Path("username")
        username : String
    ) : Call<DetailUserResponse>

    // Get Followers
    @GET ("users/{username}/followers")
    fun getDataFollowers(
        @Path("username") username : String
    ) : Call<List<ItemsItem>>

    //Get Following
    @GET("users/{username}/following")
    fun getDataFollowing(
        @Path("username") username : String
    ) : Call<List<ItemsItem>>
}