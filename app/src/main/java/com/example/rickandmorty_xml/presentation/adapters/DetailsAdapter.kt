package com.example.rickandmorty_xml.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty_xml.databinding.DetailsRecyclerviewBinding

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: DetailsRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemType: String) {

            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(
                    binding.recyclerView.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
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

    }
}