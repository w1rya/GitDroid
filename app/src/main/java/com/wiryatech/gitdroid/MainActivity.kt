package com.wiryatech.gitdroid

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.gitdroid.adapter.UserAdapter
import com.wiryatech.gitdroid.model.User
import com.wiryatech.gitdroid.ui.detail.DetailActivity
import com.wiryatech.gitdroid.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showRecyclerView()

        rv_user.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) return false
                loadingIndicator(true)
                userViewModel.setUser(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> false
        }
    }

    private fun showRecyclerView() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.adapter = adapter

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserViewModel::class.java)

        userViewModel.getUsers().observe(this, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                loadingIndicator(false)
            }
        })

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "${user.username}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.USERNAME, user.username)
        intent.putExtra(DetailActivity.AVATAR, user.avatar)
        startActivity(intent)
    }

    private fun loadingIndicator(state: Boolean) {
        if (state) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

}
