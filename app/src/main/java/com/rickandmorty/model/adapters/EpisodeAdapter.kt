package com.rickandmorty.model.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rickandmorty.EpisodeDetailsFragment
import com.rickandmorty.R
import com.rickandmorty.databinding.FragmentEpisodeBinding
import com.rickandmorty.model.api.ResultsEpisode
import dagger.hilt.android.internal.managers.ViewComponentManager

class EpisodeAdapter: PagingDataAdapter <ResultsEpisode, EpisodeAdapter.EpisodeHolder>(diffCallback) {

    class EpisodeHolder(val binding: FragmentEpisodeBinding
    ): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        return EpisodeHolder(FragmentEpisodeBinding.inflate
            (LayoutInflater.from(parent.context),
        parent, false
          )
        )
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {

            name.text = "${currentItem?.name}"
            episodenumber.text = "${currentItem?.episode}"
            airDate.text = "${currentItem?.airDate}"


        }

        holder.itemView.setOnClickListener { view ->
            val activity = (view.context as ViewComponentManager.FragmentContextWrapper).baseContext as AppCompatActivity
            val fragment = EpisodeDetailsFragment()
            val bundle = Bundle()
            bundle.putLong(
                EpisodeDetailsFragment.ARG_EPISODE_ID,
                getItem(holder.bindingAdapterPosition)!!.id
            )
            fragment.arguments = bundle
            var id = R.id.fragment_container_view
            // TODO: if(view.context.isTablet()) {}
            activity.supportFragmentManager.beginTransaction()
                .replace(id, fragment)
                .addToBackStack(null).commit()
        }
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<ResultsEpisode>(){
            override fun areItemsTheSame(oldItem: ResultsEpisode, newItem: ResultsEpisode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultsEpisode, newItem: ResultsEpisode): Boolean {
                return oldItem == newItem
            }

        }
    }
}