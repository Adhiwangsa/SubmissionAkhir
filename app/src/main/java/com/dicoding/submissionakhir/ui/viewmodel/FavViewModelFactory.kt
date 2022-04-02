package com.dicoding.submissionakhir.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavViewModelFactory (private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object{
        @Volatile
        private var INSTANCE: FavViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavViewModelFactory{
            if (INSTANCE == null){
                synchronized(FavViewModelFactory::class.java){
                    INSTANCE = FavViewModelFactory(application)
                }
            }
            return INSTANCE as FavViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return FavViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}