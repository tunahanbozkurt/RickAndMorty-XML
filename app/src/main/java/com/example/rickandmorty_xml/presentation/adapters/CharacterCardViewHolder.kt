package com.example.rickandmorty_xml.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty_xml.R
import com.example.rickandmorty_xml.databinding.CharacterCardBinding
import com.example.rickandmorty_xml.domain.model.CharacterCardModel

class CharacterCardViewHolder(
    private val binding: CharacterCardBinding,
    private val onItemClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val ALIVE = "Alive"
        const val DEAD = "Dead"
    }

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

            characterImage.load(item.imageUrl) {
                crossfade(true)
                crossfade(700)
            }
        }
    }
}