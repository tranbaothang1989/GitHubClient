package com.viettminiapps.githubclient.view.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viettminiapps.githubclient.databinding.FragmentUserListBinding
import com.viettminiapps.githubclient.utils.hideKeyboard
import com.viettminiapps.githubclient.view.adapter.UserListAdapter
import kotlinx.android.synthetic.main.fragment_user_list.*


class UserListFragment : Fragment() {

    private lateinit var adapter: UserListAdapter
    private lateinit var viewDataBinding: FragmentUserListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentUserListBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@UserListFragment).get(UserListViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObservers()

        setupView()
    }


    private fun setupObservers() {
        viewDataBinding.viewmodel?.userListLive?.observe(viewLifecycleOwner, Observer {
            if (viewDataBinding.viewmodel?.searchPage!! <= 1) {
                adapter.updateUserList(it)
            }else{
                adapter.addUserList(it)
            }
        })
    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = UserListAdapter(viewDataBinding.viewmodel!!)
            val layoutManager = LinearLayoutManager(activity)
            viewDataBinding.rvUsers.layoutManager = layoutManager
            viewDataBinding.rvUsers.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    layoutManager.orientation
                )
            )
            viewDataBinding.rvUsers.adapter = adapter
        }
    }

    private fun setupView(){
        viewDataBinding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)) {
                    // LOAD MORE
                    viewDataBinding.viewmodel?.loadMoreUserList()
                }
            }
        })
        edtSearch.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    v.hideKeyboard()
                    viewDataBinding.viewmodel?.searchName = edtSearch.text.toString()
                    viewDataBinding.viewmodel?.fetchUserList()
                }
            }
            return@setOnEditorActionListener false
        }

        btnSearch.setOnClickListener {
            it.hideKeyboard()
            viewDataBinding.viewmodel?.searchName = edtSearch.text.toString()
            viewDataBinding.viewmodel?.fetchUserList()
        }
    }
}