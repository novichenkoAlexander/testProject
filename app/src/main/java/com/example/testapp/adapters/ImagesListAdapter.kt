package com.example.testapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.models.Image


class ImagesListAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<Image, ImagesListAdapter.ItemViewHolder>(ImageDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.images_list_item, parent, false),
            ::onImageClick
        )
    }

    private fun onImageClick(position: Int) {
        onClick(getItem(position).imageUri)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        itemView: View,
        private val onImageClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.ivImageItem)
        private val checkbox = itemView.findViewById<CheckBox>(R.id.check)

        init {
            image.setOnClickListener {
                onImageClick(adapterPosition)
            }
        }

        fun bind(item: Image) {
            Glide.with(itemView)
                .load(Uri.parse(item.imageUri))
                .centerCrop()
                .into(image)
            checkbox.isChecked = item.checked
        }
    }
}

class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }
}
