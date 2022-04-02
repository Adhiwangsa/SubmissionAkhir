package com.dicoding.submissionakhir.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.submissionakhir.R
import com.dicoding.submissionakhir.database.FavEntity
import com.dicoding.submissionakhir.databinding.ActivityDetailUserBinding
import com.dicoding.submissionakhir.ui.adapter.SectionsPagerAdapter
import com.dicoding.submissionakhir.ui.viewmodel.FavViewModel
import com.dicoding.submissionakhir.ui.viewmodel.FavViewModelFactory
import com.dicoding.submissionakhir.ui.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var favoriteViewModel: FavViewModel
    private lateinit var favEntity: FavEntity

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title ="Detail"

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        favoriteViewModel = obtainViewModel(this)
        viewModel.setDetail(username.toString())
        viewModel.getDetail().observe(this){
            if (it != null){
                showLoading(false)
                binding.apply {
                    txtName.text = it.name ?: "No Name"
                    txtUsername.text = it.login
                    txtLocation.text = it.location ?: "No Location"
                    txtCompany.text = it.company ?: "No Company"
                    txtUrl.text = it.blog ?: "Nothing"
                    txtFollowers.text = "${it.followers} Followers"
                    txtFollowing.text = "${it.following} Following"
                    txtRepo.text = "${it.publicRepos} Repository"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .into(txtImageView)
                }
            }
        }
       var Checked = false
        CoroutineScope(Dispatchers.IO).launch{
            val count = favoriteViewModel.check(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if(count > 1){
                        Checked = true
                        binding.fab.isClickable = true
                        binding.fab.setImageResource(R.drawable.ic_fav)
                    }else{
                        Checked = false
                        binding.fab.isClickable = true
                    }
                }
            }
        }

        binding.fab.setOnClickListener{
            Checked =! Checked
            if (Checked){
                favoriteViewModel.insert(favEntity)
                binding.fab.setImageResource(R.drawable.ic_fav)
                Toast.makeText(this, "${favEntity.login} has been added to favorites", Toast.LENGTH_SHORT).show()
            }else {
                favoriteViewModel.delete(favEntity)
                binding.fab.setImageResource(R.drawable.ic_favorite_border)
                Toast.makeText(this, "${favEntity.login} has been removed to favorites", Toast.LENGTH_SHORT).show()
            }
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.apply {
            viewpage.adapter = sectionsPagerAdapter
            TabLayoutMediator(tablayout, viewpage){ tab, position ->
                tab.text= resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f }


        viewModel.getDetail().observe(this){
            if (it != null){
                favEntity = FavEntity(
                    id,
                    it.login,
                    it.avatarUrl,
                    it.htmlUrl
                )
            }
        }
    }


    private fun showLoading(state: Boolean) {
        if (state){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavViewModel{
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavViewModel::class.java)
    }

}