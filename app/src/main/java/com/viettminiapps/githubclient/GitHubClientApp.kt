package com.viettminiapps.githubclient

import android.app.Application

class GitHubClientApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: GitHubClientApp
    }
}