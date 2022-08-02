package com.example.shows_saratedd.show_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shows_saratedd.R
import com.example.shows_saratedd.databinding.ItemReviewBinding

class ReviewsAdapter(
    private var items: List<Review>
) : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

//    fun addReview() {
//        notifyItemInserted()
//    }

    fun getReviews(): List<Review> {
        return items
    }

    fun getAllReviews(reviews: List<Review>) {
        items = reviews
        notifyDataSetChanged()
    }


    inner class ReviewViewHolder(private var binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.reviewProfileImg.setImageResource(R.drawable.ic_profile_placeholder)
            Glide
                .with(binding.root)
                .load(review.user.imageUrl)
                .placeholder(R.drawable.ic_profile_placeholder)
                .into(binding.reviewProfileImg)
            binding.reviewUsername.text = review.user.email.substringBefore("@", "")
            binding.reviewRating.text = review.rating.toString()
            if (review.comment != "")
                binding.reviewComment.text = review.comment

        }
    }
}