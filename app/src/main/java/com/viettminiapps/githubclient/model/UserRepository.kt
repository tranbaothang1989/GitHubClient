package com.viettminiapps.githubclient.model

import com.viettminiapps.githubclient.model.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

class UserRepository {

    companion object {
        private var INSTANCE: UserRepository? = null
        fun getInstance() = INSTANCE
            ?: UserRepository().also {
                INSTANCE = it
            }
    }


    // GET User list
    fun getUserList(search: String, page: Int, onResult: (isSuccess: Boolean, response: UserResponse?) -> Unit) {

        ApiClient.instance.findUsers(search = URLEncoder.encode(search, "utf-8"), page = page)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>?, response: Response<UserResponse>?) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                    onResult(false, null)
                }

        })
    }

    //GET User Detail
    fun getUserDetail(loginName: String, onResult: (isSuccess: Boolean, response: UserProfile?) -> Unit) {

        ApiClient.instance.getUser(userName = loginName)
            .enqueue(object : Callback<UserProfile> {
                override fun onResponse(call: Call<UserProfile>?, response: Response<UserProfile>?) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<UserProfile>?, t: Throwable?) {
                    onResult(false, null)
                }

            })
    }
}