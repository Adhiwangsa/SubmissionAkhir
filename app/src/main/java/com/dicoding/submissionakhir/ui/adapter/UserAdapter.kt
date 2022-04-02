package com.dicoding.submissionakhir.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionakhir.database.data.DataUser
import com.dicoding.submissionakhir.databinding.UserRowBinding

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val listUser = ArrayList<DataUser>()
    private var onItemClickCallback: OnItemClickCallback? = null


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    fun setList(users: java.util.ArrayList<DataUser>){
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: UserRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: DataUser){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .into(circleImg)
                tvItemUsername.text = user.login
                tvItemUrl.text = user.html_url
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val view = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback{
        fun onItemClicked(data: DataUser)
    }
}