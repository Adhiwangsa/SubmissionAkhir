package com.dicoding.submissionakhir.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionakhir.database.FavEntity
import com.dicoding.submissionakhir.databinding.UserRowBinding

class FavAdapter : RecyclerView.Adapter<FavAdapter.FavoriteViewHolder>() {

    private val listFavorite = ArrayList<FavEntity>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setListFavorites(favorite: java.util.ArrayList<FavEntity>) {
        listFavorite.clear()
        listFavorite.addAll(favorite)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(val binding: UserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favEntity: FavEntity) {
            with(binding) {
                Glide.with(itemView)
                    .load(favEntity.avatarUrl)
                    .into(circleImg)
                tvItemUsername.text = favEntity.login
                tvItemUrl.text = favEntity.htmlurl
            }

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(favEntity)
            }
        }
    }

    override fun getItemCount(): Int = listFavorite.size

    interface OnItemClickCallback{
        fun onItemClicked(fav: FavEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }


}