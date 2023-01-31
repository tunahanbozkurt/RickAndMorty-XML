package com.example.rickandmorty_xml.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmorty_xml.databinding.CharacterCardBinding
import com.example.rickandmorty_xml.domain.model.CharacterCardModel

class CharactersPagingAdapter(private val onItemClickListener: (Int) -> Unit) :
    PagingDataAdapter<CharacterCardModel, CharacterCardViewHolder>(diffCallback) {

    companion object {
        const val ITEM_TYPE = 1001

        val diffCallback = object : DiffUtil.ItemCallback<CharacterCardModel>() {
            override fun areItemsTheSame(
                oldItem: CharacterCardModel,
                newItem: CharacterCardModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CharacterCardModel,
                newItem: CharacterCardModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: CharacterCardViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item) }
    }

    override fun getItemViewType(position: Int): Int {
        return ITEM_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterCardViewHolder {
        val binding = CharacterCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterCardViewHolder(binding) {
            onItemClickListener(getItem(it)?.id ?: 1)
        }
    }
}





