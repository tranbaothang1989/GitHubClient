package com.viettminiapps.githubclient.view.ui.userlist

import androidx.lifecycle.MutableLiveData
import com.viettminiapps.githubclient.model.User
import com.viettminiapps.githubclient.model.UserRepository
import com.viettminiapps.githubclient.utils.Constants
import com.viettminiapps.githubclient.view.base.BaseViewModel

class UserListViewModel : BaseViewModel() {

    val userListLive = MutableLiveData<MutableList<User>>()
    var total: Int = 0

    var searchName: String = ""
    var searchPage: Int = 1

    fun fetchUserList() {
        if (dataLoading.value==true)
            return

        searchPage = 1
        dataLoading.value = true
        UserRepository.getInstance().getUserList(searchName,searchPage) { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                if(userListLive.value == null)
                    userListLive.value = mutableListOf()
                else
                    userListLive.value?.clear()

                response?.items?.let {
                    userListLive.value?.addAll(it)
                }
                userListLive.value  = userListLive.value

                total = response?.total_count!!
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }

    fun loadMoreUserList() {
        if (dataLoading.value==true)
            return

        if (total==0 || searchPage*Constants.NUM_OF_PAGE >= total)
            return

        searchPage++

        dataLoading.value = true
        UserRepository.getInstance().getUserList(searchName,searchPage) { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                response?.items?.let { userListLive.value?.addAll(it) }
                userListLive.value  = userListLive.value
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }
}