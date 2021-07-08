package com.example.testapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.example.testapp.R
import com.example.testapp.models.Image


class ImagesListAdapter : ListAdapter<Image, ImagesListAdapter.ItemViewHolder>(ImageDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.images_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<TextView>(R.id.ivImageItem)

        fun bind(item: Image) {
            image.text = item.image
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
