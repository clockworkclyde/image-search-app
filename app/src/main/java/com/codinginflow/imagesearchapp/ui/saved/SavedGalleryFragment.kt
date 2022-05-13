package com.codinginflow.imagesearchapp.ui.saved

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.databinding.FragmentSavedGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedGalleryFragment : Fragment(R.layout.fragment_saved_gallery) {

    private lateinit var binding: FragmentSavedGalleryBinding
    private lateinit var photoAdapter: InternalUnsplashPhotoAdapter

    private val viewModel by viewModels<SavedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedGalleryBinding.bind(view)
        photoAdapter = InternalUnsplashPhotoAdapter(null)

        binding.apply {
            recyclerView.apply {
                adapter = photoAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.loadImagesFromStorage()
        viewModel.savedFiles.observe(viewLifecycleOwner) {
            it?.let {
                photoAdapter.submitList(it)
                println("adapter size is ${photoAdapter.itemCount}, and ${photoAdapter.currentList}")

            } ?: Toast.makeText(context, "Failed to display recycler", Toast.LENGTH_SHORT)
                .show()
        }

        /*ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = photoAdapter.currentList[viewHolder.bindingAdapterPosition]
            }
        })*/
    }
}
