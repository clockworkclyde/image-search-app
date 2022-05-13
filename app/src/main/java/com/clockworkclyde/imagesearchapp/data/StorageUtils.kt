package com.clockworkclyde.imagesearchapp.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.clockworkclyde.imagesearchapp.models.InternalUnsplashPhoto
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class StorageUtils @Inject constructor(@ActivityContext private val context: Context) {

    suspend fun loadPhotosFromInternalStorage(): List<InternalUnsplashPhoto> {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bmp: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalUnsplashPhoto(it.name, bmp)
            } ?: listOf()
        }
    }

    fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
            context.openFileOutput("$filename.jpg", Context.MODE_PRIVATE).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save photo")
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun deletePhotoFromInternalStorage(filename: String): Boolean {
        return try {
            context.deleteFile(filename)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }

}