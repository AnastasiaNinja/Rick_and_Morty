package com.rickandmorty.model.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.example.ResultsLocation
import com.rickandmorty.LocationDetailsFragment
import com.rickandmorty.R
import com.rickandmorty.databinding.FragmentLocationBinding
import com.rickandmorty.model.api.ResultsEpisode
import com.rickandmorty.model.locations.entities.Location

class LocationAdapter: PagingDataAdapter <ResultsLocation, LocationAdapter.LocationHolder>(diffCallback) {


    class LocationHolder(val binding: FragmentLocationBinding
    ): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        return LocationHolder(FragmentLocationBinding.inflate(LayoutInflater.from(parent.context),
            parent, false
          )
        )
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {

            nameLocation.text = "${currentItem?.name}"
            locationType.text = "${currentItem?.type}"
            dimension.text = "${currentItem?.dimension}"
        }
//        holder.bind(locations[position])
//        holder.itemView.setOnClickListener { view ->
//            val activity: AppCompatActivity = view.context as AppCompatActivity
//            val fragment = LocationDetailsFragment()
//            val bundle = Bundle()
//            bundle.putParcelable(
//                LocationDetailsFragment.ARG_LOCATION,
//            locations[holder.bindingAdapterPosition]
//            )
//            fragment.arguments = bundle
//            var id = R.id.fragment_container_view
//            //TODO: if(view.context.isTablet()) {}
//            activity.supportFragmentManager
//                .beginTransaction()
//                .replace(id, fragment)
//                .addToBackStack(null).commit()
//        }
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<ResultsLocation>(){
            override fun areItemsTheSame(oldItem: ResultsLocation, newItem: ResultsLocation): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultsLocation, newItem: ResultsLocation): Boolean {
                return oldItem == newItem
            }

        }
    }
}