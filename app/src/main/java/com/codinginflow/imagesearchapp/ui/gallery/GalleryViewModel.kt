package com.codinginflow.imagesearchapp.ui.gallery

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.codinginflow.imagesearchapp.data.UnsplashRepository
import com.codinginflow.imagesearchapp.ui.saved.InternalUnsplashPhotoAdapter
import kotlinx.coroutines.launch


class GalleryViewModel @ViewModelInject constructor(
    private val repository: UnsplashRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    private val refreshStorage = MutableLiveData()

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    //internal storage integration
    fun loadPhotosFromInternalStorageToRecycler(adapter: InternalUnsplashPhotoAdapter, context: Context) {
        viewModelScope.launch {
            val photos = repository.loadPhotosFromInternalStorage(context)
        }
    }


    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "cats"
    }
}