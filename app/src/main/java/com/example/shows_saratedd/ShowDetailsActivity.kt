package com.example.shows_saratedd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shows_saratedd.databinding.ActivityShowDetailsBinding
import com.example.shows_saratedd.databinding.DialogAddReviewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import show.Review
import show.ReviewsAdapter
import kotlin.properties.Delegates

class ShowDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowDetailsBinding
    lateinit var adapter: ReviewsAdapter
    var rat = 0f
    var recInit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.detailsTitle.text = intent.extras?.getString("showName")
        intent.extras?.getInt("showImage")?.let { binding.detailsImg.setImageResource(it) }
//        binding.detailsImg.setImageResource(intent.extras?.getInt("showImage"))
        binding.detailsDesc.text = intent.extras?.getString("showDesc")
        val username = intent.extras?.getString("user")

        binding.detailsBackButton.setOnClickListener {
            val intentBack = Intent(this, ShowsActivity::class.java)
            startActivity(intentBack)
        }
        if (username != null) {
            initDialog(username)
        } else {
            initDialog("anoniman")
        }
    }


    private fun initDialog(username : String) {
        binding.detailsReviewsButton.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val bottomSheetBinding = DialogAddReviewBinding.inflate(layoutInflater)
            dialog.setContentView(bottomSheetBinding.root)
            if (!recInit) {
                initReviewsRecycler()
                recInit = true
            }
            bottomSheetBinding.submitButton.setOnClickListener {
//                if (zvijezde nisu oznacene) {
//                    do nothing ili error da nisu oznacene, ili submitbutton disabled
//                }
                addReviewToList(
                    username,
                    bottomSheetBinding.dialogCommentInputEdit.text.toString(),
                    bottomSheetBinding.dialogRating.getRating().toInt()
                )
//                treba nam ime oosbe i rating iz ratingbara
                dialog.dismiss()
                binding.detailsData.isVisible = true
                binding.ratingBar.isVisible = true
                binding.detailsRecycler.isVisible = true
                binding.detailsReviewsMessage.isVisible = false
                updateRating()

            }
            dialog.show()
        }
    }
    private fun addReviewToList(name: String, comment: String, ratingNum: Int) {
        adapter.addReview(Review(name, comment, ratingNum, R.drawable.ic_profile_placeholder))
    }

    private fun initReviewsRecycler() {
        adapter = ReviewsAdapter(emptyList())
        binding.detailsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        binding.detailsRecycler.adapter = adapter
        binding.detailsRecycler.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
    }

    private fun updateRating() {
        rat = adapter.updateRating()
        binding.ratingBar.setRating(rat)
        binding.ratingBar.setIsIndicator(true)

        binding.detailsData.text = adapter.itemCount.toString() +" reviews, " + String.format("%.2f", rat) + " average"
    }
}