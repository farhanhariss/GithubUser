package com.farhanharis.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET ("search/users")
    fun getUser(
        @Path("username") username : String
    ) : Call<`GithubResponse.kt`>
}