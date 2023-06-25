package com.example.libnet.room.entry

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HOME_BANNER")
data class HomeBannerEntry(
    @PrimaryKey
    val id: Long,
    val imagePath: String?,
    val desc: String?
)