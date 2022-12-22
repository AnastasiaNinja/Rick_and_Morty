package com.rickandmorty.model.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rickandmorty.model.api.ResultsLocation
import com.rickandmorty.LocationDetailsFragment
import com.rickandmorty.R
import com.rickandmorty.databinding.FragmentLocationBinding
import dagger.hilt.android.internal.managers.ViewComponentManager

class LocationAdapter: PagingDataAdapter <ResultsLocation, LocationAdapter.LocationHolder>(diffCallback) {


    class LocationHolder(val binding: FragmentLocationBinding
    ): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        return LocationHolder(FragmentLocationBinding.inflate
            (LayoutInflater.from(parent.context),
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

        holder.itemView.setOnClickListener { view ->
            val activity = (view.context as ViewComponentManager.FragmentContextWrapper).baseContext as AppCompatActivity
            val fragment = LocationDetailsFragment()
            val bundle = Bundle()
            bundle.putLong(
                LocationDetailsFragment.ARG_LOCATION_ID,
            getItem(holder.bindingAdapterPosition)!!.id
            )
            fragment.arguments = bundle
            var id = R.id.fragment_container_view
            //TODO: if(view.context.isTablet()) {}
            activity.supportFragmentManager
                .beginTransaction()
                .replace(id, fragment)
                .addToBackStack(null).commit()
        }
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