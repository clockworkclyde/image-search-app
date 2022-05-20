package com.clockworkclyde.imagesearchapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.clockworkclyde.imagesearchapp.database.UnsplashDatabase
import com.clockworkclyde.imagesearchapp.providers.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val db: UnsplashDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getUnsplashResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            //pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query, null) },
            remoteMediator = UnsplashRemoteMediator(query, db, unsplashApi)
        ) {
            db.unsplashPhotoDao().pagingSource()
        }.flow


}