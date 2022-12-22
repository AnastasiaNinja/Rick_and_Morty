package com.rickandmorty

import android.app.Person
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.rickandmorty.model.api.ApiService
import com.rickandmorty.model.api.ResultsLocation
import com.rickandmorty.model.locations.entities.Location
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LocationDetailsFragment : Fragment() {
    private lateinit var binding: FragmentLocationDetailsBinding

    @Inject
    lateinit var apiService: ApiService

    private val id: Long
        get() = requireArguments().getLong(ARG_LOCATION_ID)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
       loadData()


        return binding.root
    }

    private fun loadData() {
        lifecycleScope.launch {
            val responseDetails = apiService.getLocationDetails(id)
            handleLocationDetails(responseDetails.body()!!)
        }
    }

    private fun handleLocationDetails(details: ResultsLocation) {
        binding.name.text = details.name
        binding.locationType.text = details.type
        binding.dimension.text = details.dimension


        val args = Bundle()
        args.putStringArrayList("ids", details.residents)


        val fragment = PersonFragment()
        fragment.arguments = args
        parentFragmentManager.beginTransaction()
            .add(binding.containerPersons.id, fragment).commit()
    }

    companion object {

        const val ARG_LOCATION_ID = "ARG_LOCATION_ID"

        @JvmStatic
        fun newInstance(location: Location): LocationDetailsFragment {
            val fragment = LocationDetailsFragment()
            fragment.arguments = bundleOf(ARG_LOCATION_ID to location)
            return fragment
        }
    }
}