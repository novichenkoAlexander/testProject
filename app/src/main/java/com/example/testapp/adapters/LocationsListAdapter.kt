package com.example.testapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.*
import com.example.testapp.R
import com.example.testapp.models.Location
import kotlinx.android.synthetic.main.locations_list_item.view.*

class LocationsListAdapter(
    private val onAddClick: (Location) -> Unit,
    private val onTextChanged: (String, Location) -> Unit,
) : ListAdapter<Location, LocationsListAdapter.ItemViewHolder>(DiffCallback()) {

    private var commonImagesList: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.locations_list_item, parent, false),
            ::onAddImageClick,
            ::onTitleTextChanged
        )
    }

    private fun onTitleTextChanged(title: String, position: Int) {
        onTextChanged(title, getItem(position))
    }

    private fun onAddImageClick(position: Int) {
        onAddClick(getItem(position))
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(
        view: View,
        private val onAddImageClick: (Int) -> (Unit),
        private val onTitleTextChanged: (String, Int) -> (Unit),
    ) : RecyclerView.ViewHolder(view) {


        init {
            itemView.fabAddImage.setOnClickListener {
                onAddImageClick(adapterPosition)
            }
            itemView.etLocationTitle.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val text: String = itemView.etLocationTitle.text.toString()
                    onTitleTextChanged(text, adapterPosition)
                    return@setOnEditorActionListener true
                } else {
                    false
                }
            }
            //TODO: ADD feature to save title when focus gone or keyboard collapse
        }

        fun bind(location: Location) {
            itemView.etLocationTitle.setText(location.title)
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
