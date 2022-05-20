package com.clockworkclyde.imagesearchapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.clockworkclyde.imagesearchapp.R
import com.clockworkclyde.imagesearchapp.models.UnsplashPhoto
import com.clockworkclyde.imagesearchapp.databinding.FragmentGalleryBinding
import com.clockworkclyde.imagesearchapp.ui.viewmodels.GalleryViewModel
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery),
    UnsplashPhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()

    lateinit var binding: FragmentGalleryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //postponeEnterTransition()
        //        view.doOnPreDraw { startPostponedEnterTransition() }

        binding = FragmentGalleryBinding.bind(view)
        val unsplashAdapter = UnsplashPhotoAdapter(this)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null
            adapter =
                unsplashAdapter.withLoadStateHeaderAndFooter(
                    header = UnsplashPhotoLoadStateAdapter { unsplashAdapter.retry() },
                    footer = UnsplashPhotoLoadStateAdapter { unsplashAdapter.retry() }
                )
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collectLatest {
                unsplashAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

        unsplashAdapter.addLoadStateListener { loadState ->
            binding.apply {
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // if its empty
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && unsplashAdapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)

        val searchItem = menu.findItem(R.id.button_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (!query.isNullOrBlank()) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onItemClick(transitionView: View, photo: UnsplashPhoto) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.unsplash_motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.unsplash_motion_duration_large).toLong()
        }
        val photoDetailTransitionName = getString(R.string.photo_detail_transition_name)
        val extras = FragmentNavigatorExtras(transitionView to photoDetailTransitionName)
        val directions = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment2(photo)
        findNavController().navigate(directions, extras)
    }

}