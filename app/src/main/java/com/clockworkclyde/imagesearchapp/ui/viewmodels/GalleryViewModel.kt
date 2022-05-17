package com.clockworkclyde.imagesearchapp.ui.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.clockworkclyde.imagesearchapp.data.UnsplashRepository
import com.clockworkclyde.imagesearchapp.models.UnsplashPhoto

class GalleryViewModel @ViewModelInject constructor(
    private val repository: UnsplashRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    private val currentOrderBy = MutableLiveData(CURRENT_ORDER)
    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    val feed = currentOrderBy.switchMap { order ->
        repository.getFeedResults(order).cachedIn(viewModelScope)
    }

    fun getUnsplashFeedOrderBy(value: String) {
        currentOrderBy.value = value
    }

    companion object {
        private const val CURRENT_ORDER = "latest"
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "cats"
    }
}