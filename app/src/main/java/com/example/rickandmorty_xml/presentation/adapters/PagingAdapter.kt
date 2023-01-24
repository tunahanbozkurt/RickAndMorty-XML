package com.example.rickandmorty_xml.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty_xml.R
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.Result
import com.example.rickandmorty_xml.databinding.CharacterCardBinding

class PagingAdapter(private val onItemClickListener: () -> Unit) :
    PagingDataAdapter<Result, PagingAdapter.ViewHolder>(diffCallback) {

    companion object {
        const val ITEM_TYPE = 1001

        val diffCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(binding: CharacterCardBinding) : RecyclerView.ViewHolder(binding.root) {
        private val textView: TextView = binding.characterName
        private val imageView: ImageView = binding.characterImage
        private val characterCard: ViewGroup = binding.characterCard

        init {

            characterCard.setOnClickListener {
                onItemClickListener()
            }
        }

        fun bind(item: Result?) {
            textView.text =
                item?.name ?: itemView.context.getString(R.string.couldnt_loaded_message)
            imageView.load(item?.image) {
                crossfade(true)
                crossfade(1000)
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
}





