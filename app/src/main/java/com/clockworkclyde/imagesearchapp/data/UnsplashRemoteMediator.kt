package com.clockworkclyde.imagesearchapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.clockworkclyde.imagesearchapp.models.UnsplashPhoto
import com.clockworkclyde.imagesearchapp.models.UnsplashRemoteKeys
import com.clockworkclyde.imagesearchapp.database.UnsplashDatabase
import com.clockworkclyde.imagesearchapp.providers.UnsplashApi
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

const val INITIAL_PAGE = 1

@ExperimentalPagingApi
class UnsplashRemoteMediator(
    private val query: String,
    private val database: UnsplashDatabase,
    private val api: UnsplashApi
) : RemoteMediator<Int, UnsplashPhoto>() {

    private val dao = database.unsplashPhotoDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashPhoto>
    ): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE
                }
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val remoteKeys =
                        getRemoteKeyForLastItem(state) ?: throw InvalidObjectException("Is empty")
                    remoteKeys.nextKey ?: return MediatorResult.Success(true)
                }
            }

            val response = api.searchPhotos(query, page, state.config.pageSize)
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeyDao().removeRemoteKeys()
                    database.unsplashPhotoDao().clearAllPhotos()
                }
                val prevKey = if (page == INITIAL_PAGE) null else page - 1
                val nextKey = if (response.results.isEmpty()) null else page + 1
                val keys = response.results.map {
                    UnsplashRemoteKeys(it.id, prevKey, nextKey)
                }
                database.remoteKeyDao().insertAll(keys)
                database.unsplashPhotoDao().insertAllPhotos(response.results)
            }
            MediatorResult.Success(response.results.size < state.config.pageSize)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UnsplashPhoto>): UnsplashRemoteKeys? {
        return state.lastItemOrNull()?.let { photo ->
            database.withTransaction { database.remoteKeyDao().remoteKeysById(photo.id) }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UnsplashPhoto>): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.withTransaction { database.remoteKeyDao().remoteKeysById(id) }
            }
        }
    }

}