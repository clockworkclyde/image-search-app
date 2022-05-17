package com.clockworkclyde.imagesearchapp.data

import androidx.paging.PagingSource
import com.clockworkclyde.imagesearchapp.models.UnsplashPhoto
import com.clockworkclyde.imagesearchapp.providers.UnsplashApi
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_PAGING_STARTING_INDEX = 1

class FeedPagingSource (
    private val unsplashApi: UnsplashApi,
    private val orderBy: String
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_PAGING_STARTING_INDEX

        return try {

            val photos = unsplashApi.getPhotosFeed(orderBy, position, params.loadSize)

            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_PAGING_STARTING_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}