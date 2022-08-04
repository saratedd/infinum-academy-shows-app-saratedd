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
    private val context: Context,
    private val onItemClickCallback: (Show) -> Unit
//    adapter treba viewholder
) : RecyclerView.Adapter<ShowsAdapter.ShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = ViewShowItemBinding.inflate(LayoutInflater.from(parent.context), parent)
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        holder.bind(items[position])
    }

    //      the adapter needs to know how many items are there in the list (starting and ending indices)
//    item.size
    override fun getItemCount(): Int = items.count()

    fun addAllShows(shows: List<Show>) {
        items = shows
        notifyDataSetChanged()
    }

//    fun addShow(show: Show) {
//        items = items + show
//        notifyItemInserted(items.lastIndex)
//    }

    inner class ShowViewHolder(private var binding: ViewShowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        RecyclerView.ViewHolder(ShowCardView(context)) {
        fun bind(show: Show) {
//            binding.showTitle.text = show.title
//            Glide
//                .with(binding.root)
//                .load(show.imageUrl)
//                .placeholder(R.drawable.ic_office)
//                .into(binding.showImage)
//            binding.showDesc.text = show.description
            ShowCardView(context).setTitle(show.title)
            ShowCardView(context).setDesc(show.description)
            ShowCardView(context).setImage(show.imageUrl)

            binding.cardConatiner.setOnClickListener {
//                treba prikazati podatke o showu
                onItemClickCallback(show)
            }
        }
    }
}