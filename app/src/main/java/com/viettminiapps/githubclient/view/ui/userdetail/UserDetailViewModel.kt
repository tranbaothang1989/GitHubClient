package com.viettminiapps.githubclient.view.ui.userdetail

import androidx.lifecycle.MutableLiveData
import com.viettminiapps.githubclient.model.UserProfile
import com.viettminiapps.githubclient.model.UserRepository
import com.viettminiapps.githubclient.view.base.BaseViewModel

class UserDetailViewModel : BaseViewModel() {

    val userProfile = MutableLiveData<UserProfile>()

    fun fetchUser(userName: String) {
        if (dataLoading.value==true)
            return
        dataLoading.value = true
        UserRepository.getInstance().getUserDetail(userName){ isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                userProfile.value = response
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }
}