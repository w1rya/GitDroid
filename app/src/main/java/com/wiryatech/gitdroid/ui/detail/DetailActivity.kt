package com.wiryatech.gitdroid.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.adapter.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val USERNAME = "username"
        const val AVATAR = "avatar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val username = intent.getStringExtra(USERNAME)
        val avatar = intent.getStringExtra(AVATAR)

        tv_username.text = username
        Glide.with(this)
            .load(avatar!!)
            .apply(RequestOptions().override(128, 128))
            .into(iv_profile)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        sectionsPagerAdapter.setData(username!!.toString())
        vp_detail.adapter = sectionsPagerAdapter
        tab_detail.setupWithViewPager(vp_detail)

        supportActionBar?.elevation = 0f

        val mBundle = Bundle()
        mBundle.putString(DetailFollowerFragment.USERNAME, username)
        val mFollowerFragment = DetailFollowerFragment()
        mFollowerFragment.arguments = mBundle
        val mFollowingFragment = DetailFollowingFragment()
        mFollowingFragment.arguments = mBundle
        Log.d("BundleFragment", mFollowerFragment.arguments.toString())
    }
}