package com.example.testapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.example.testapp.R


class ImagesListAdapter(
    private val imagesList: List<String>,
) : RecyclerView.Adapter<ImagesListAdapter.ItemViewHolder>() {

    private var images: List<String> = ArrayList()

    init {
        images = imagesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.images_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    inner class ItemViewHolder(
        private val itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<TextView>(R.id.ivImageItem)

        fun bind(item: String) {
            image.text = item
        }

    }


}