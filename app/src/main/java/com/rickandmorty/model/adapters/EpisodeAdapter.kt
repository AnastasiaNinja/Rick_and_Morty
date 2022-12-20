package com.rickandmorty.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rickandmorty.databinding.FragmentEpisodeBinding
import com.rickandmorty.model.api.ResultsEpisode

class EpisodeAdapter: PagingDataAdapter <ResultsEpisode, EpisodeAdapter.EpisodeHolder>(diffCallback) {

    class EpisodeHolder(val binding: FragmentEpisodeBinding
    ): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        return EpisodeHolder(FragmentEpisodeBinding.inflate(LayoutInflater.from(parent.context),
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

//        holder.c(episodes[position])
//        holder.itemView.setOnClickListener { view ->
//            val activity: AppCompatActivity = view.context as AppCompatActivity
//            val fragment = EpisodeDetailsFragment()
//            val bundle = Bundle()
//            bundle.putParcelable(
//                EpisodeDetailsFragment.ARG_EPISODE,
//                episodes[holder.bindingAdapterPosition]
//            )
//            fragment.arguments = bundle
//            var id = R.id.fragment_container_view
//            // TODO: if(view.context.isTablet()) {}
//            activity.supportFragmentManager.beginTransaction()
//                .replace(id, fragment)
//                .addToBackStack(null).commit()
//        }
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