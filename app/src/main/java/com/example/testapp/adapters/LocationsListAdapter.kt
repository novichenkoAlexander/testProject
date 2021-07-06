package com.example.testapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.testapp.R
import com.example.testapp.models.Location
import kotlinx.android.synthetic.main.locations_list_item.view.*

class LocationsListAdapter(
    private val onAddClick: (Location) -> Unit,
) : ListAdapter<Location, LocationsListAdapter.ItemViewHolder>(DiffCallback()) {

    private var commonImagesList: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.locations_list_item, parent, false),
            ::onAddImageClick
        )
    }

    private fun onAddImageClick(position: Int) {
        onAddClick(getItem(position))
        commonImagesList.add("Hello")
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        view: View,
        private val onAddImageClick: (Int) -> (Unit),
    ) : RecyclerView.ViewHolder(view) {


        init {
            itemView.fabAddImage.setOnClickListener {
                onAddImageClick(adapterPosition)
            }
        }

        fun bind(location: Location) {
            itemView.etLocationTitle.setText(location.title)
            val childAdapter = ImagesListAdapter(location.imageList)
            itemView.rvImages.layoutManager = GridLayoutManager(itemView.context, 3)
            itemView.rvImages.adapter = childAdapter
        }

    }
}

class DiffCallback : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }

}
