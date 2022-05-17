package com.clockworkclyde.imagesearchapp.ui.details

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.clockworkclyde.imagesearchapp.R
import com.clockworkclyde.imagesearchapp.databinding.FragmentDetailsBinding
import com.clockworkclyde.imagesearchapp.ui.viewmodels.SavedViewModel
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModels<SavedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_main
            duration = resources.getInteger(R.integer.unsplash_motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)

        setHasOptionsMenu(true)

        binding.apply {
            val photo = args.photo

            Glide.with(this@DetailsFragment)
                .asBitmap()
                .load(photo.urls.regular)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        savingPhotoTextView.isVisible = false
                        textViewDescription.isVisible = photo.description != null
                        textViewCreator.isVisible = true
                        return false
                    }
                })
                .error(R.drawable.ic_launcher_foreground)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageView.setImageBitmap(resource).also {
                            saveImageBtn.setOnClickListener {
                                try {
                                    val isSaveSuccessfully =
                                        viewModel.savePhotosInStorage(photo.id, resource)
                                    if (isSaveSuccessfully) {
                                        Toast.makeText(
                                            context,
                                            "Saved successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else Toast.makeText(
                                        context,
                                        "Failed to save",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        context,
                                        "Error! Cant load this image",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    e.printStackTrace()
                                }
                            }
                        }

                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        imageView.setImageBitmap(null)
                    }
                })

            textViewDescription.text = photo.description
            val uri = Uri.parse(photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            textViewCreator.apply {
                text = "Photo by ${photo.user.name}"
                setOnClickListener {
                    startActivity(intent)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_details, menu)
    }


}