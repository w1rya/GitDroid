package com.wiryatech.gitdroid.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.wiryatech.gitdroid.BuildConfig
import com.wiryatech.gitdroid.model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UserViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUser(username: String) {
        val listItems = ArrayList<User>()

        val apiKey = BuildConfig.API_KEY //"1ec3900a49ec284ebfad23aeec5a3065f6b119c6"
        val url = "https://api.github.com/search/users?q=$username"

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token $apiKey")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObjects = JSONObject(result)
                    val items = responseObjects.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val user = items.getJSONObject(i)
                        val userItems = User()
                        userItems.username = user.getString("login")
                        userItems.avatar = user.getString("avatar_url")
                        listItems.add(userItems)
                        Log.d("LIST", url)
                    }
                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Log.d("onFailure", error?.message.toString())
            }

        })
    }

    fun getUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }
}