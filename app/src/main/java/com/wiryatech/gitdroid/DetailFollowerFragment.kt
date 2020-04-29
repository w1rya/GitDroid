package com.wiryatech.gitdroid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.gitdroid.adapter.FollowerAdapter
import com.wiryatech.gitdroid.viewmodel.FollowerViewModel
import kotlinx.android.synthetic.main.fragment_detail_follower.*

class DetailFollowerFragment : Fragment() {

    private lateinit var followerViewModel: FollowerViewModel
    private lateinit var adapter: FollowerAdapter

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val test = arguments?.getString(USERNAME)
        Log.d("BundleFragment1", test.toString())
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_follower, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d("BundleFragment1", "$savedInstanceState, $arguments")
        if (arguments != null) {
            val username = arguments?.getString(USERNAME)
            Log.d("BundleFragment1", "$arguments")
            followerViewModel.setFollower(username.toString())
        }
        showRecyclerView()
        rv_follower.setHasFixedSize(true)
    }

    private fun showRecyclerView() {
        adapter = FollowerAdapter()
        adapter.notifyDataSetChanged()

        rv_follower.layoutManager = LinearLayoutManager(context)
        rv_follower.adapter = adapter

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowerViewModel::class.java)

        followerViewModel.getFollowers().observe(viewLifecycleOwner, Observer { followerItems ->
            if (followerItems != null) {
                adapter.setData(followerItems)
            }
        })
    }
}