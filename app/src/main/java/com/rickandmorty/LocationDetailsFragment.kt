package com.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.rickandmorty.databinding.FragmentLocationBinding
import com.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.rickandmorty.databinding.FragmentMainScreenBinding
import com.rickandmorty.model.locations.LocationService
import com.rickandmorty.model.locations.entities.Location

class LocationDetailsFragment : Fragment() {
    private lateinit var binding: FragmentLocationDetailsBinding

    private val locationService: LocationService
      get() = (context?.applicationContext as App).locationService

    private val location: Location
       get() = requireArguments().getParcelable<Location>(ARG_LOCATION) as Location


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // TODO:
        return inflater.inflate(R.layout.fragment_location_details, container, false)
    }

    companion object {

        const val ARG_LOCATION = "ARG_LOCATION"

        @JvmStatic
        fun newInstance(location: Location): LocationDetailsFragment {
            val fragment = LocationDetailsFragment()
            fragment.arguments = bundleOf(ARG_LOCATION to location)
            return fragment
        }
    }
}