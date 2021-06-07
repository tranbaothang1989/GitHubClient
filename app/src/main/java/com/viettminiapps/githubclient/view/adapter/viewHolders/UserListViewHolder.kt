package com.viettminiapps.githubclient.view.adapter.viewHolders

import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.viettminiapps.githubclient.BR
import com.viettminiapps.githubclient.R
import com.viettminiapps.githubclient.model.User
import com.viettminiapps.githubclient.utils.Constants
import com.viettminiapps.githubclient.view.ui.userlist.UserListViewModel
import kotlinx.android.synthetic.main.item_view_user_list.view.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.sdk27.coroutines.onClick

class UserListViewHolder constructor(private val dataBinding: ViewDataBinding,
                                     private val userListViewModel: UserListViewModel)
    : RecyclerView.ViewHolder(dataBinding.root) {

    val avatarImage = itemView.imgUserAvatar
    fun bindData(userData: User) {
        dataBinding.setVariable(BR.userData, userData)
        dataBinding.executePendingBindings()

        Glide
            .with(itemView.context)
            .load(userData.avatar_url)
            .centerCrop()
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_account)
            .into(avatarImage)

        itemView.onClick {
            val bundle = bundleOf(Constants.ARG_USER to userData.login)
            itemView.findNavController().navigate(R.id.action_UserList_to_UserDetail, bundle)
        }
    }
}