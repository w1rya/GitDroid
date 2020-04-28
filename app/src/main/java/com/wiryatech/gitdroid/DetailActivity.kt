package com.wiryatech.gitdroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.tv_username
import kotlinx.android.synthetic.main.item_user.*

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        vp_detail.adapter = sectionsPagerAdapter
        tab_detail.setupWithViewPager(vp_detail)

        supportActionBar?.elevation = 0f

        val mFollowerFragment = DetailFollowerFragment()
        val mBundle = Bundle()
        mBundle.putString(DetailFollowerFragment.USERNAME, username)
        mFollowerFragment.arguments = mBundle
    }
}