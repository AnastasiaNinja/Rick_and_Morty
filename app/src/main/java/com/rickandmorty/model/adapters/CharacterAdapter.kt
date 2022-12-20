package com.rickandmorty.model.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rickandmorty.CharactersDetailsFragment
import com.rickandmorty.R
import com.rickandmorty.databinding.FragmentPersonBinding
import com.rickandmorty.model.api.ResultsCharacters
import dagger.hilt.android.internal.managers.ViewComponentManager.FragmentContextWrapper

class CharacterAdapter: PagingDataAdapter<ResultsCharacters, CharacterAdapter.CharacterHolder>(diffCallback) {

    class CharacterHolder(val binding: FragmentPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        return CharacterHolder(FragmentPersonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
           )
        )

    }

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {

            name.text = "${currentItem?.name}"
            species.text = "${currentItem?.species}"
            status.text = "${currentItem?.status}"
            gender.text = "${currentItem?.gender}"
             currentItem?.image
                Glide.with(imageCharacter.context)
                    .load(currentItem?.image)
                    .circleCrop()
                    .placeholder(R.drawable.personicon)
                    .into(imageCharacter)

            }
//
        holder.itemView.setOnClickListener { view ->
            val activity = (view.context as FragmentContextWrapper).baseContext as AppCompatActivity
            val fragment = CharactersDetailsFragment()
            val bundle = Bundle()
            bundle.putLong(
                CharactersDetailsFragment.ARG_CHARACTER_ID,
                getItem(holder.bindingAdapterPosition)!!.id
            )
            fragment.arguments = bundle
            val id = R.id.fragment_container_view
            // TODO: if (view.context.isTable)
            activity.supportFragmentManager.beginTransaction()
                .replace(id, fragment)
                .addToBackStack(null).commit()
        }


    }


    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<ResultsCharacters>(){
            override fun areItemsTheSame(oldItem: ResultsCharacters, newItem: ResultsCharacters): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultsCharacters, newItem: ResultsCharacters): Boolean {
                return oldItem == newItem
            }

        }
    }
}

