package com.dicoding.submissionakhir.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionakhir.R
import com.dicoding.submissionakhir.databinding.FollowersBinding
import com.dicoding.submissionakhir.ui.DetailUserActivity
import com.dicoding.submissionakhir.ui.adapter.UserAdapter
import com.dicoding.submissionakhir.ui.viewmodel.UserViewModel

class Followers: Fragment(R.layout.followers) {
    private var _binding: FollowersBinding?= null
    private val binding get() = _binding!!
    private lateinit var  viewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FollowersBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        viewModel.setFollowers(username)
        viewModel.get().observe(viewLifecycleOwner, {
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }
    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state : Boolean){
        if (state){
            binding.loadBar.visibility = View.VISIBLE
        }else{
            binding.loadBar.visibility = View.GONE
        }
    }
}
