package com.wiryatech.gitdroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.wiryatech.gitdroid.model.Follower
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowerViewModel : ViewModel() {

    val listFollowers = MutableLiveData<ArrayList<Follower>>()

    fun setFollower(username: String) {
        val listItems = ArrayList<Follower>()

        val apiKey = "1ec3900a49ec284ebfad23aeec5a3065f6b119c6"
        val url = "https://api.github.com/users/$username/followers"

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
                        val follower = responseObject.getJSONObject(i)
                        val followerItems = Follower()
                        followerItems.username = follower.getString("login")
                        followerItems.avatar = follower.getString("avatar_url")
                        listItems.add(followerItems)
                        Log.d("FOLLOWER", url)
                    }
                    listFollowers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Follower Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Follower onFailure", error?.message.toString())
            }
        })
    }

    fun getFollowers(): LiveData<ArrayList<Follower>> {
        return listFollowers
    }
}