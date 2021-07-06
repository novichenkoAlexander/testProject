package com.example.testapp.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLocationsBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.testapp.adapters.ImagesListAdapter
import com.example.testapp.adapters.LocationsListAdapter
import com.example.testapp.models.Location
import kotlin.random.Random

class LocationsFragment : Fragment(R.layout.fragment_locations) {

    private val viewBinding: FragmentLocationsBinding by viewBinding()

    private lateinit var adapter: LocationsListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationsRecyclerView = viewBinding.rvLocations

        adapter = LocationsListAdapter(
            onAddClick = { location ->
                location.imageList.add("Hello")
            }
        )
        locationsRecyclerView.adapter = adapter
        var list: ArrayList<Location> = ArrayList()


        viewBinding.fabAddLocation.setOnClickListener {
            val newList = ArrayList<Location>()
            newList.addAll(list)
            val random = Random.nextLong(50)
            val location = Location(random)
            newList.add(location)
            list = newList
            adapter.submitList(list)
        }

    }

}