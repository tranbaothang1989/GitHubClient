package com.viettminiapps.githubclient.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viettminiapps.githubclient.databinding.ItemViewUserListBinding
import com.viettminiapps.githubclient.model.User
import com.viettminiapps.githubclient.view.adapter.viewHolders.UserListViewHolder
import com.viettminiapps.githubclient.view.ui.userlist.UserListViewModel

class UserListAdapter (private val userListViewModel: UserListViewModel) : RecyclerView.Adapter<UserListViewHolder>() {
    var userList: ArrayList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ItemViewUserListBinding.inflate(inflater, parent, false)
        return UserListViewHolder(dataBinding, userListViewModel)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bindData(userList[position])
    }

    fun updateUserList(userList: List<User>) {
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    fun addUserList(userList: List<User>) {
        val before = this.userList.size
        this.userList.addAll(userList.subList(before,userList.size))
        val after = this.userList.size
        notifyItemRangeInserted(before, after)
    }
}