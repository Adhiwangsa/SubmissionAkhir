package com.dicoding.submissionakhir.ui

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionakhir.R
import com.dicoding.submissionakhir.database.FavEntity
import com.dicoding.submissionakhir.databinding.ActivityFavoriteBinding
import com.dicoding.submissionakhir.ui.adapter.FavAdapter
import com.dicoding.submissionakhir.ui.viewmodel.FavViewModel
import com.dicoding.submissionakhir.ui.viewmodel.FavViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private lateinit var adapter: FavAdapter
    private lateinit var  viewModel: FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Favorite User"

        viewModel = obtainViewModel(this)

        adapter = FavAdapter()
        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.setHasFixedSize(true)
        binding?.rvUser?.adapter = adapter

        viewModel.getAllFavorites().observe(this){
            if (it !=null){
                val list = mappedList(it)
                adapter.setListFavorites(list)
            }
        }

        adapter.setOnClickCallback(object : FavAdapter.OnItemClickCallback{
            override fun onItemClicked(fav: FavEntity){
                intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, fav.login)
                intent.putExtra(DetailUserActivity.EXTRA_ID, fav.id)

                startActivity(intent)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavViewModel{
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavViewModel::class.java)
    }

    private fun mappedList(list: List<FavEntity>):ArrayList<FavEntity>{
        val listFavorite = ArrayList<FavEntity>()
        for (_favoriteUser in list){
            val favUser = FavEntity(
                _favoriteUser.id,
                _favoriteUser.login,
                _favoriteUser.avatarUrl,
                _favoriteUser.htmlurl
            )
            listFavorite.add(favUser)
        }
        return listFavorite
    }

    override fun onSupportNavigateUp(): Boolean{
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}