package com.clockworkclyde.imagesearchapp.di

//import com.codinginflow.imagesearchapp.data.UnsplashDatabase
import com.clockworkclyde.imagesearchapp.providers.UnsplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)


    /*
    @Singleton
    @Provides
    fun provideUnsplashDatabase(
        app: Application) = Room.databaseBuilder(app, UnsplashDatabase::class.java, "unsplash_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideUnsplashDao(database: UnsplashDatabase) = database.unsplashPhotoDao()

    */
    }


