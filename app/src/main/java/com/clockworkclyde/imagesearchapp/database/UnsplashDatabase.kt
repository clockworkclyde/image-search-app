package com.clockworkclyde.imagesearchapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.clockworkclyde.imagesearchapp.models.UnsplashPhoto
import com.clockworkclyde.imagesearchapp.models.UnsplashRemoteKeys

@Database(entities = [UnsplashPhoto::class, UnsplashRemoteKeys::class], version = 1, exportSchema = false)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashPhotoDao(): UnsplashPhotoDao
    abstract fun remoteKeyDao(): UnsplashRemoteKeysDao

    class Callback: RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
        }
    }

}