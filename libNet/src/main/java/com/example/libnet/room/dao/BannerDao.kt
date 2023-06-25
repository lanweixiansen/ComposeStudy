package com.example.libnet.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.libnet.room.entry.HomeBannerEntry

@Dao
interface BannerDao {
    @Query("SELECT * FROM HOME_BANNER ORDER BY id")
    fun getPlants(): LiveData<List<HomeBannerEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(banners: List<HomeBannerEntry>)
}