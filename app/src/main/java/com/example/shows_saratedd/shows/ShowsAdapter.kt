package com.example.shows_saratedd.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shows_saratedd.R
import com.example.shows_saratedd.databinding.ViewShowItemBinding

class ShowsAdapter(
    private var items: List<Show>,
    private var filteredItems: List<Show>,
    private val onItemClickCallback: (Show) -> Unit
//    adapter treba viewholder
) : RecyclerView.Adapter<ShowsAdapter.ShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
//        val binding = ViewShowItemBinding.inflate(LayoutInflater.from(parent.context), parent)
        return ShowViewHolder(ShowCardView(parent.context))
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    fun addAllShows(shows: List<Show>) {
        items = shows
        notifyDataSetChanged()
    }

    fun filterList(text: String) {
        for (show in items) {
            if (show.title.lowercase().contains(text.lowercase())) {
                filteredItems = filteredItems + show
            }
        }
        items = filteredItems
        notifyDataSetChanged()
    }

    inner class ShowViewHolder(private var binding: ShowCardView) :
        RecyclerView.ViewHolder(binding) {

        fun bind(show: Show) {
            binding.setTitle(show.title)
            binding.setDesc(show.description)
            binding.setImage(show.imageUrl)

            binding.setOnClickListener {
//                treba prikazati podatke o showu
                onItemClickCallback(show)
            }
        }
    }
}