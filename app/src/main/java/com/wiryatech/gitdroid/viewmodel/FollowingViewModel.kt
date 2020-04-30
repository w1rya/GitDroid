package com.wiryatech.gitdroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.wiryatech.gitdroid.model.Follower
import com.wiryatech.gitdroid.model.Following
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {

    val listFollowings = MutableLiveData<ArrayList<Following>>()

    fun setFollowing(username: String) {
        val listItems = ArrayList<Following>()

        val apiKey = "1ec3900a49ec284ebfad23aeec5a3065f6b119c6"
        val url = "https://api.github.com/users/$username/following"

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token $apiKey")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val following = responseObject.getJSONObject(i)
                        val followingItems = Following()
                        followingItems.username = following.getString("login")
                        followingItems.avatar = following.getString("avatar_url")
                        listItems.add(followingItems)
                        Log.d("FOLLOWING", url)
                    }
                    listFollowings.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Following Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Following onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<Following>> {
        return listFollowings
    }
}