package com.wiryatech.gitdroid.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.adapter.FollowingAdapter
import com.wiryatech.gitdroid.viewmodel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_detail_following.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFollowingFragment : Fragment() {

    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: FollowingAdapter

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_following, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showRecyclerView()
        rv_following.setHasFixedSize(true)
        loadingIndicator(true)

        if (arguments != null) {
            val username = arguments?.getString(USERNAME)
            followingViewModel.setFollowing(username.toString())
            Log.d("BundleFragment1", "$arguments")
        }
    }

    private fun showRecyclerView() {
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()

        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.adapter = adapter

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)

        followingViewModel.getFollowing().observe(viewLifecycleOwner, Observer { followingItems ->
            if (followingItems != null) {
                adapter.setData(followingItems)
                loadingIndicator(false)
            }
        })
    }

    private fun loadingIndicator(state: Boolean) {
        if (state) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

}
