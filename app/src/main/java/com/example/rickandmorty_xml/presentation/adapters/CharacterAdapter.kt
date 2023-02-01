package com.example.rickandmorty_xml.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty_xml.databinding.CharacterCardBinding
import com.example.rickandmorty_xml.domain.model.CharacterCardModel

class CharacterAdapter(
    private val characterList: List<CharacterCardModel>,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<CharacterCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterCardViewHolder {
        val binding = CharacterCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterCardViewHolder(binding) {
            onItemClickListener(characterList[it].id)
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharacterCardViewHolder, position: Int) {
        holder.bind(characterList[position])
    }
}