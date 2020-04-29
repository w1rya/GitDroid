package com.wiryatech.gitdroid.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wiryatech.gitdroid.ui.detail.DetailFollowingFragment
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.ui.detail.DetailFollowerFragment

class SectionsPagerAdapter(private val mContext: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)  {

    var username = "test"

    companion object {
        const val USERNAME = "username"
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.follower,
        R.string.following
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment =
                    DetailFollowerFragment()
                val mBundle = Bundle()
                mBundle.putString(USERNAME, getData())
                fragment.arguments = mBundle
                Log.d("BundleFragmentVP", fragment.arguments.toString())
            }
            1 -> fragment =
                DetailFollowingFragment()
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

    fun setData(user: String) {
        username = user
    }

    private fun getData(): String {
        return username
    }
}