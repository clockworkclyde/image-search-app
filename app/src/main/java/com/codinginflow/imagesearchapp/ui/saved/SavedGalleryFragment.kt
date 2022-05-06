package com.codinginflow.imagesearchapp.ui.saved

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.databinding.FragmentDetailsBinding
import com.codinginflow.imagesearchapp.databinding.FragmentSavedGalleryBinding
import com.codinginflow.imagesearchapp.models.InternalUnsplashPhoto
import com.codinginflow.imagesearchapp.ui.gallery.GalleryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedGalleryFragment : Fragment(R.layout.fragment_saved_gallery) {

    private lateinit var binding: FragmentSavedGalleryBinding
    private lateinit var adapter: InternalUnsplashPhotoAdapter

    private val viewModel by viewModels<GalleryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedGalleryBinding.bind(view)
        adapter = InternalUnsplashPhotoAdapter(null)

        binding.apply {
            recyclerView.apply {
                adapter = adapter
                setHasFixedSize(true)
            }

            viewModel.loadPhotosFromInternalStorageToRecycler(adapter, requireContext())

            ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val item = adapter.currentList[viewHolder.bindingAdapterPosition]

                }
            })
        }
    }

    private fun loadPhotosFromInternalStorageToRecycler() {

    }

}