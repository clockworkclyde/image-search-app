package com.clockworkclyde.imagesearchapp.ui.saved

import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clockworkclyde.imagesearchapp.data.StorageUtils
import com.clockworkclyde.imagesearchapp.models.InternalUnsplashPhoto
import kotlinx.coroutines.launch
import java.lang.Exception

class SavedViewModel @ViewModelInject constructor(
    private val storageUtils: StorageUtils
) : ViewModel() {

    private var _savedFiles = MutableLiveData<List<InternalUnsplashPhoto>>()
    val savedFiles: LiveData<List<InternalUnsplashPhoto>> get() = _savedFiles

    fun loadImagesFromStorage() {
        viewModelScope.launch {
            val loadResult = storageUtils.loadPhotosFromInternalStorage()
            _savedFiles.value = loadResult
        }
    }

    fun savePhotosInStorage(fileName: String, bmp: Bitmap): Boolean {
        return try {
            viewModelScope.launch {
                storageUtils.savePhotoToInternalStorage(fileName, bmp)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun onPhotoSwipedLeft(photo: InternalUnsplashPhoto) = viewModelScope.launch {
        storageUtils.deletePhotoFromInternalStorage(photo.fileName)
    }
}