package com.farhanharis.githubuser

import android.provider.Contacts.SettingsColumns.KEY
import com.farhanharis.githubuser.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: $KEY")
    @GET ("search/users")
    fun getSearchData(
        @Query ("q") query: String
    ) : Call<GithubResponse>


    fun getUserData(
        @Path("username") username : String
    ) : Call<`GithubResponse`>
}