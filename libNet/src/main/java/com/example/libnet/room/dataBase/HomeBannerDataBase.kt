package com.example.libnet.room.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lib_base.manager.AppManager
import com.example.libnet.room.dao.BannerDao
import com.example.libnet.room.entry.HomeBannerEntry

@Database(entities = [HomeBannerEntry::class], version = 1, exportSchema = false)
abstract class HomeBannerDataBase : RoomDatabase() {
    abstract fun getBannerDao(): BannerDao

    companion object {
        @Volatile
        private var instance: HomeBannerDataBase? = null

        @JvmStatic
        fun newInstance(): HomeBannerDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase().also { instance = it }
            }
        }

        private fun buildDataBase(): HomeBannerDataBase {
            return Room.databaseBuilder(
                AppManager.getApplicationContext(),
                HomeBannerDataBase::class.java,
                "banner_db"
            ).build()
        }
    }
}