package com.example.rickandmorty_xml.presentation.main_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty_xml.R
import com.example.rickandmorty_xml.domain.model.CharacterList
import com.squareup.picasso.Picasso

class CharacterListAdapter(private val dataSet: CharacterList):
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val name: TextView

        init {
            name = view.findViewById(R.id.name)
            image = view.findViewById(R.id.character_image)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.character_card, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = dataSet.list[position].name
        Picasso.get().load(dataSet.list[position].image_url).into(viewHolder.image)
    }

    override fun getItemCount() = dataSet.list.size
}
