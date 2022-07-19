package com.example.shows_saratedd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shows_saratedd.databinding.ActivityShowDetailsBinding
import com.example.shows_saratedd.databinding.DialogAddReviewBinding
import com.example.shows_saratedd.databinding.ItemReviewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ShowDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.detailsTitle.text = intent.extras?.getString("showName")
        binding.detailsDesc.text = intent.extras?.getString("showDesc")
        intent.extras?.getInt("showImage")?.let { binding.detailsImg.setImageResource(it) }
//        binding.detailsImg.setImageResource(intent.extras?.getInt("showImage"))

        binding.detailsBackButton.setOnClickListener {
            val intentBack = Intent(this, ShowsActivity::class.java)
            startActivity(intentBack)
        }
        binding.detailsReviewsButton.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val bottomSheetBinding = DialogAddReviewBinding.inflate(layoutInflater)
            dialog.setContentView(bottomSheetBinding.root)

            bottomSheetBinding.submitButton.setOnClickListener {
//                if (zvijezde nisu oznacene) {
//                    do nothing ili error da nisu oznacene, ili submitbutton disabled
//                }
                dialog.dismiss()
            }
            dialog.show()
        }
    }
}