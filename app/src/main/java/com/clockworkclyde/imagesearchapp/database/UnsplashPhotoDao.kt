package com.clockworkclyde.imagesearchapp.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clockworkclyde.imagesearchapp.models.UnsplashPhoto

@Dao
interface UnsplashPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photos: List<UnsplashPhoto>)

    @Query("SELECT * FROM UnsplashPhoto")
    fun pagingSource(): PagingSource<Int, UnsplashPhoto>

    @Query("DELETE FROM UnsplashPhoto")
    suspend fun clearAllPhotos()

}