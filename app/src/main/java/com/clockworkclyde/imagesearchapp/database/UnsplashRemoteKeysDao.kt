package com.clockworkclyde.imagesearchapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.clockworkclyde.imagesearchapp.models.UnsplashRemoteKeys

@Dao
interface UnsplashRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKeys: List<UnsplashRemoteKeys>)

    @Query("SELECT * FROM UnsplashRemoteKeys WHERE id=:id")
    fun remoteKeysById(id: String): UnsplashRemoteKeys?

    @Query("DELETE FROM UnsplashRemoteKeys")
    fun removeRemoteKeys()

}