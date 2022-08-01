package com.example.shows_saratedd.show_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shows_saratedd.R
import com.example.shows_saratedd.databinding.ItemReviewBinding

class ReviewsAdapter(
    private var items: List<Review>
) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context))
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    fun addReview(review: Review) {
        items = items + review
        notifyItemInserted(items.lastIndex)
    }

    fun getReviews(): List<Review> {
        return items
    }

//    fun updateRating(): Float {
//        var rating = 0f
//        for (item in items)
//            rating += item.rating
//        return rating / items.count()
//    }

    inner class ReviewViewHolder(private var binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.reviewProfileImg.setImageResource(R.drawable.ic_profile_placeholder)
            binding.reviewUsername.text = review.name
            binding.reviewRating.text = review.rating.toString()
            if (review.comment != "")
                binding.reviewComment.text = review.comment //ali samo ako postoji

        }

    }
}