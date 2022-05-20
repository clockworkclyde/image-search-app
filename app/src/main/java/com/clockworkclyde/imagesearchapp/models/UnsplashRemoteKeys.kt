package com.clockworkclyde.imagesearchapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UnsplashRemoteKeys(
    @PrimaryKey
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
