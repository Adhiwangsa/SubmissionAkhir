package com.dicoding.submissionakhir.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavEntity::class], version = 1)
abstract class FavDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    companion object{
        @JvmStatic
        fun getDatabase(context: Context): FavDatabase{
            if (INSTANCE == null){ synchronized(FavDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavDatabase::class.java, "favorite_database"
                    )
                        .build()
                }
            }
            return INSTANCE as FavDatabase
        }
        @Volatile
        private var INSTANCE: FavDatabase? = null
    }
}