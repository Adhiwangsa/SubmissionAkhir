package com.dicoding.submissionakhir.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submissionakhir.database.FavDatabase
import com.dicoding.submissionakhir.database.FavEntity
import com.dicoding.submissionakhir.database.FavoritesDao
import com.dicoding.submissionakhir.ui.model.FavRepository

class FavViewModel (application: Application): ViewModel() {

    private val mFavoriteRepository: FavRepository = FavRepository(application)
    private var favDatabase: FavDatabase? = FavDatabase.getDatabase(application)
    private var favoritesDao: FavoritesDao? = favDatabase?.favoritesDao()

    fun getAllFavorites(): LiveData<List<FavEntity>> = mFavoriteRepository.getAllFavorites()

    fun insert(favEntity: FavEntity){
        mFavoriteRepository.insert(favEntity)
    }
    fun delete(favEntity: FavEntity){
        mFavoriteRepository.delete(favEntity)
    }

    fun check(id: Int) = favoritesDao?.check(id)
}