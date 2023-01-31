package com.example.rickandmorty_xml.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty_xml.databinding.DetailsRecyclerviewBinding

class DetailsAdapter(
    val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: DetailsRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.recyclerView.apply {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DetailsRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder
    }
}