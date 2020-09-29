package com.molette.uniq.presentation.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.molette.uniq.R
import com.molette.uniq.databinding.CharacterCellBinding
import com.molette.uniq.presentation.models.Character

class CharacterAdapter():
    PagingDataAdapter<Character, CharacterViewHolder>(CHARACTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<CharacterCellBinding>(inflater, R.layout.character_cell, parent,  false)

        return CharacterViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        val character = getItem(position)
        character?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }
}

class CharacterViewHolder(val binding: CharacterCellBinding, val context: Context): RecyclerView.ViewHolder(binding.root){

    fun bind(character: Character){
        binding.character = character
        Glide.with(context)
            .load(character.thumbnail+"/standard_large."+character.extension)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.characterThumbnail)
    }
}