package com.example.rickandmorty_xml.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty_xml.databinding.CharacterCardBinding
import com.example.rickandmorty_xml.databinding.LoadStateFooterBinding

class PagingLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<PagingLoadStateAdapter.ViewHolder>() {

    companion object {
        const val ITEM_TYPE = 1002
    }

    inner class ViewHolder(private val binding: LoadStateFooterBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
               /*TODO*/
            }
        }
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return ITEM_TYPE
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = LoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
}