package com.codinginflow.imagesearchapp.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.databinding.ItemSavedPhotoBinding
import com.codinginflow.imagesearchapp.models.InternalUnsplashPhoto

class InternalUnsplashPhotoAdapter(private val onPhotoClick: ((InternalUnsplashPhoto) -> Unit)?) :
    ListAdapter<InternalUnsplashPhoto, InternalUnsplashPhotoAdapter.PhotoViewHolder>(Companion) {

    class PhotoViewHolder(private val binding: ItemSavedPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: InternalUnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .asBitmap()
                    .load(photo.bmp)
                    .centerCrop()
                    .into(imageView)
            }
        }
    }

    companion object : DiffUtil.ItemCallback<InternalUnsplashPhoto>() {
        override fun areItemsTheSame(
            oldItem: InternalUnsplashPhoto,
            newItem: InternalUnsplashPhoto
        ) = oldItem.fileName == newItem.fileName

        override fun areContentsTheSame(
            oldItem: InternalUnsplashPhoto,
            newItem: InternalUnsplashPhoto
        ) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemSavedPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }


}