package com.viettminiapps.githubclient.model.api

import com.viettminiapps.githubclient.model.UserProfile
import com.viettminiapps.githubclient.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun findUsers(@Query("q") search: String = "",
                @Query("sort") sort: String = "repositories",
                  @Query("page") page: Int = 0): Call<UserResponse>

    @GET("users/{user}")
    fun getUser(@Path("user") userName: String): Call<UserProfile>
}