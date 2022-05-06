package com.codinginflow.imagesearchapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codinginflow.imagesearchapp.models.UnsplashPhoto
import com.codinginflow.imagesearchapp.providers.UnsplashApi

/*@ExperimentalPagingApi
class UnsplashRemoteMediator(
    private val query: String,
    private val database: UnsplashDatabase,
    private val api: UnsplashApi
) : RemoteMediator<Int, UnsplashPhoto>() {

    val dao = database.unsplashPhotoDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashPhoto>
    ): MediatorResult {

        val loadKey = when (loadType) {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page. // In this example, you never need to prepend, since REFRESH
            //      // will always load the first page in the list. Immediately
            //      // return, reporting end of pagination.
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                lastItem.id
                }
            }

        val response = api.searchPhotos(query = query)
        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                dao.deleteById()
            }
        }
        }
    }*/
