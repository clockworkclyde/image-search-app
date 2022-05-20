package com.clockworkclyde.imagesearchapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.clockworkclyde.imagesearchapp.R
import com.clockworkclyde.imagesearchapp.databinding.FragmentFeedBinding
import com.clockworkclyde.imagesearchapp.models.UnsplashPhoto
import com.clockworkclyde.imagesearchapp.ui.gallery.UnsplashPhotoAdapter
import com.clockworkclyde.imagesearchapp.ui.gallery.UnsplashPhotoLoadStateAdapter
import com.clockworkclyde.imagesearchapp.ui.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FeedFragment : Fragment(R.layout.fragment_feed), UnsplashPhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()

    lateinit var binding: FragmentFeedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFeedBinding.bind(view)

        val feedAdapter = UnsplashPhotoAdapter(this)

        binding.recyclerView.apply {
            adapter = feedAdapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { feedAdapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { feedAdapter.retry() }
            )
            setHasFixedSize(true)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collectLatest {
                feedAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

    }

    override fun onItemClick(transitionView: View, photo: UnsplashPhoto) {
        //TODO
    }
}