package com.clockworkclyde.imagesearchapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.clockworkclyde.imagesearchapp.data.UnsplashRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
class GalleryViewModel @ViewModelInject constructor(
    private val repository: UnsplashRepository,
    //@Assisted state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = MutableStateFlow<String>(DEFAULT_QUERY)

    val photos = currentQuery.flatMapLatest { queryString ->
        repository.getUnsplashResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        //private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "cats"
    }
}