package com.example.testapp.dialogs

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.testapp.R

class ImageDialog(private val imageUri: Uri?) : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_image_dialog, container, false)
        val image = view.findViewById<ImageView>(R.id.imageView)
        loadImage(image)
        return view
    }

    private fun loadImage(image: ImageView) {
        Glide.with(this)
            .load(imageUri)
            .centerCrop()
            .into(image)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 1)
        dialog?.window?.setLayout(width, width)
    }

    companion object {
        const val DIALOG_TAG = "IMAGE FRAGMENT"
    }
}