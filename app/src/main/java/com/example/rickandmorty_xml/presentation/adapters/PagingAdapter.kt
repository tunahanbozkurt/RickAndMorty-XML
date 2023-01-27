package com.example.rickandmorty_xml.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty_xml.R
import com.example.rickandmorty_xml.databinding.CharacterCardBinding
import com.example.rickandmorty_xml.domain.model.CharacterCardModel

class PagingAdapter(private val onItemClickListener: (Int) -> Unit) :
    PagingDataAdapter<CharacterCardModel, PagingAdapter.ViewHolder>(diffCallback) {

    companion object {
        const val ITEM_TYPE = 1001
        const val CROSS_FADE_MILLIS = 1000
        const val ALIVE = "Alive"
        const val DEAD = "Dead"

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

    inner class ViewHolder(
        private val binding: CharacterCardBinding,
        private val onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.characterCard.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
        }

        fun bind(item: CharacterCardModel) {
            binding.apply {
                characterName.text = item.characterName
                origin.text = item.origin
                lastKnownLocation.text = item.lastKnownLocation

                isAliveAndSpecies.text = binding.isAliveAndSpecies
                    .context.getString(
                        R.string.isAliveSpecies,
                        item.isAlive,
                        item.species
                    )

                isAliveDot.setImageResource(
                    when (item.isAlive) {
                        ALIVE -> {
                            R.drawable.green_dot
                        }
                        DEAD -> {
                            R.drawable.red_dot
                        }
                        else -> {
                            R.drawable.gray_dot
                        }
                    }
                )

                characterImage.load(item.characterImageUrl) {
                    crossfade(true)
                    crossfade(CROSS_FADE_MILLIS)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item) }
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
        return ViewHolder(binding) {
            onItemClickListener(getItem(it)?.id ?: 1)
        }
    }
}





