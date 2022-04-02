package com.dicoding.submissionakhir.ui.model

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.submissionakhir.database.FavDatabase
import com.dicoding.submissionakhir.database.FavEntity
import com.dicoding.submissionakhir.database.FavoritesDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepository (application: Application) {
    private val mFavoriteDao: FavoritesDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init{
        val db = FavDatabase.getDatabase(application)
        mFavoriteDao= db.favoritesDao()
    }
    fun getAllFavorites(): LiveData<List<FavEntity>> = mFavoriteDao.getAllNotes()

    fun insert(favEntity: FavEntity){
        executorService.execute { mFavoriteDao.insert(favEntity) }
    }
    fun delete(favEntity: FavEntity){
        executorService.execute { mFavoriteDao.delete(favEntity) }
    }


}