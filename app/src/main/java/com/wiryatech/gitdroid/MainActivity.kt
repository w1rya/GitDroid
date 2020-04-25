package com.wiryatech.gitdroid

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.gitdroid.adapter.UserAdapter
import com.wiryatech.gitdroid.model.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.adapter = adapter

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(UserViewModel::class.java)

        userViewModel.getUsers().observe(this, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
            }
        })

        btnCity.setOnClickListener {
            val user = editCity.text.toString()
            if (user.isEmpty()) return@setOnClickListener
            userViewModel.setUser(user)
        }

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
            R.id.detail -> {
                startActivity(Intent(this, DetailActivity::class.java))
                true
            }
            else -> false
        }
    }
}
