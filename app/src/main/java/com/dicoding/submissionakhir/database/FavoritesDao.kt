package com.dicoding.submissionakhir.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favEntity: FavEntity)

    @Delete
    fun delete(favEntity: FavEntity)

    @Query("SELECT * from FavEntity ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<FavEntity>>

    @Query("SELECT count(*) FROM FavEntity WHERE FavEntity.id = :id")
    fun check(id : Int): Int
}
