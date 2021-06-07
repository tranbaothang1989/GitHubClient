package com.viettminiapps.githubclient.view.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.viettminiapps.githubclient.R
import com.viettminiapps.githubclient.databinding.FragmentUserDetailBinding
import com.viettminiapps.githubclient.utils.Constants
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetailFragment : Fragment(){

    private lateinit var viewDataBinding: FragmentUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentUserDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        viewModel = ViewModelProviders.of(this@UserDetailFragment).get(UserDetailViewModel::class.java)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        var userName = arguments?.getString(Constants.ARG_USER)
        userName?.let {
            if (viewModel.empty.value!!) {
                viewModel.fetchUser(it)
            }
        }
    }

    private fun setupObservers() {
        viewModel.userProfile.observe(viewLifecycleOwner, Observer {
            viewDataBinding.user = it

            Glide
                .with(this)
                .load(it.avatar_url)
                .centerCrop()
                .placeholder(R.drawable.ic_account)
                .error(R.drawable.ic_account)
                .into(imgUserAvatar)

        })
    }
}