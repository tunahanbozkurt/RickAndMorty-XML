package com.example.rickandmorty_xml.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty_xml.R
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.Result

class PagingAdapter: PagingDataAdapter<Result, PagingAdapter.ViewHolder>(diffCallback) {
    
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }
    
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.character_name)
        private val imageView: ImageView = view.findViewById(R.id.character_image)

        fun bind(item: Result?) {
            textView.text = item?.name ?: "Couldn't load"
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_card, parent, false)
        return ViewHolder(view)
    }
}




