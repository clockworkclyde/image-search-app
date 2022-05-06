package com.codinginflow.imagesearchapp.providers

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codinginflow.imagesearchapp.models.UnsplashPhoto
/*
@Dao
interface UnsplashPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<UnsplashPhoto>)

    @Query("SELECT * FROM unsplash_photo_table WHERE id LIKE :query")
    fun pagingSource(query: String): PagingSource<Int, UnsplashPhoto>

    @Query("DELETE FROM unsplash_photo_table")
    suspend fun clearAll()

    @Query("DELETE FROM unsplash_photo_table WHERE id LIKE :id")
    suspend fun deleteById(id: String)

}*/