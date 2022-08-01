package com.example.shows_saratedd.show_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.shows_saratedd.R
//import com.example.shows_saratedd.databinding.ActivityShowDetailsBinding
import com.example.shows_saratedd.databinding.DialogAddReviewBinding
import com.example.shows_saratedd.databinding.FragmentShowDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ShowDetailsFragment : Fragment() {
//    lateinit var binding: ActivityShowDetailsBinding
    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: ReviewsAdapter

    private val args by navArgs<ShowDetailsFragmentArgs>()
    private val viewModel by viewModels<ShowsDetailsViewModel>()

    var rat = 0f
    var recInit = false

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityShowDetailsBinding.inflate(layoutInflater)
//
//        setContentView(binding.root)
//
//        binding.detailsTitle.text = intent.extras?.getString(ShowsFragment.EXTRA_SHOW_NAME)
//        intent.extras?.getInt(ShowsFragment.EXTRA_SHOW_IMAGE)
//            ?.let { binding.detailsImg.setImageResource(it) }
////        binding.detailsImg.setImageResource(intent.extras?.getInt("showImage"))
//        binding.detailsDesc.text = intent.extras?.getString(ShowsFragment.EXTRA_SHOW_DESC)
//        val username = intent.extras?.getString(ShowsFragment.EXTRA_USER)
//
//        binding.detailsBackButton.setOnClickListener {
//            val intentBack = Intent(this, ShowsFragment::class.java)
//            startActivity(intentBack)
//        }
//        if (username != null) {
//            initDialog(username)
//        } else {
//            initDialog("anoniman")
//        }
//    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailsTitle.text = args.showName
        binding.detailsDesc.text = args.showDescription
        Glide
            .with(binding.root)
            .load(args.showImage)
            .placeholder(R.drawable.ic_office)
            .into(binding.detailsImg)
//        binding.detailsImg.setImageResource(args.showImage)

        val email = args.email
        initBackButton(email)

        initDialog(email)
    }

    private fun initBackButton(email: String) {
        binding.detailsBackButton.setOnClickListener {
            var directions = ShowDetailsFragmentDirections.toShowsFragment(email)
            findNavController().navigate(directions)
        }
    }

    private fun initDialog(email: String) {
        binding.detailsReviewsButton.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val bottomSheetBinding = DialogAddReviewBinding.inflate(layoutInflater)
            dialog.setContentView(bottomSheetBinding.root)
            if (!recInit) {
                initReviewsRecycler()
                recInit = true
            }
            bottomSheetBinding.submitButton.setOnClickListener {
                addReviewToList(
                    email.substringBefore("@", ""),
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
        binding.detailsRecycler.layoutManager =
//            zasto ovdje radi samo 'context' a na 91 treba 'requierConetxt()'
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        binding.detailsRecycler.adapter = adapter
        binding.detailsRecycler.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun updateRating() {
        rat = adapter.updateRating()
        binding.ratingBar.setRating(rat)
        binding.ratingBar.setIsIndicator(true)

        binding.detailsData.text =
            adapter.itemCount.toString() + " reviews, " + String.format("%.2f", rat) + " average"
//        binding.detailsData.text =
    //        getString(R.string.blalbla, adapter.itemCount.toString(), String.format("%.2f", rat))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}