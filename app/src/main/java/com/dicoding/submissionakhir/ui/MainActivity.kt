package com.dicoding.submissionakhir.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionakhir.R
import com.dicoding.submissionakhir.database.data.DataUser
import com.dicoding.submissionakhir.databinding.ActivityMainBinding
import com.dicoding.submissionakhir.theme.ThemePreferences
import com.dicoding.submissionakhir.ui.adapter.UserAdapter
import com.dicoding.submissionakhir.ui.viewmodel.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : UserViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var themeViewModel: ThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataUser) {
                intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                startActivity(intent)
            }
        })

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
            search.setOnClickListener {
                search()
            }
            txtSearch.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    search()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        viewModel.get().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }

        val pref = ThemePreferences.getInstance(dataStore)
        themeViewModel = ViewModelProvider(this, ThemeFactory(pref))[ThemeViewModel::class.java]
        Theme()
    }
    private fun search(){
        binding.apply{
            val query = txtSearch.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearch(query)
        }

    }
    private fun showLoading(state: Boolean){
        if (state){
            binding.loadBar.visibility = View.VISIBLE
        }else{
            binding.loadBar.visibility = View.GONE
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {

                val i = Intent(this, ThemeActivity::class.java)
                startActivity(i)
                return true
            }

            R.id.fav_menu -> {

                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
    fun Theme() {
        themeViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
 }
