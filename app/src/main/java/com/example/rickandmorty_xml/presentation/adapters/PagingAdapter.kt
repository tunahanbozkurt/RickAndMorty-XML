package com.example.rickandmorty_xml.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty_xml.R
import com.example.rickandmorty_xml.data.remote.dto.getAllCharacters.Result
import com.example.rickandmorty_xml.databinding.CharacterCardBinding

class PagingAdapter(private val onItemClickListener: (Int) -> Unit) :
    PagingDataAdapter<Result, PagingAdapter.ViewHolder>(diffCallback) {

    companion object {
        const val ITEM_TYPE = 1001
        const val CROSS_FADE_MILLIS = 1000

        val diffCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
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

        fun bind(item: Result?) {
            binding.apply {

                characterName.text =
                    item?.name.toString().replaceFirstChar { it.uppercase() }

                characterImage.load(item?.image) {
                    crossfade(true)
                    crossfade(CROSS_FADE_MILLIS)
                }

                isAliveDot.setImageResource(
                    when (item?.status) {
                        "Alive" -> {
                            R.drawable.green_dot
                        }
                        "Dead" -> {
                            R.drawable.red_dot
                        }
                        else -> {
                            R.drawable.gray_dot
                        }
                    }
                )
                origin.text = item?.origin?.name.toString().replaceFirstChar { it.uppercase() }

                isAliveAndSpecies.text = binding.isAliveAndSpecies
                    .context.getString(
                        R.string.isAliveSpecies,
                        item?.status.toString().replaceFirstChar { it.uppercase() },
                        item?.species.toString().replaceFirstChar { it.uppercase() }
                    )

                lastKnownLocation.text = item?.location?.name.toString().replaceFirstChar { it.uppercase() }
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
        return ViewHolder(binding) {
            onItemClickListener(getItem(it)?.id ?: 1)
        }
    }
}





