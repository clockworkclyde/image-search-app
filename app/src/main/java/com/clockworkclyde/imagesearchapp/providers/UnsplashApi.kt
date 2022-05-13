package com.clockworkclyde.imagesearchapp.providers

import com.clockworkclyde.imagesearchapp.BuildConfig
import com.clockworkclyde.imagesearchapp.models.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 10
    ): UnsplashResponse

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("/photos")
    suspend fun getPhotosFeed(
        @Query("query") query: String,
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 10
    )
}