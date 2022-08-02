package com.example.shows_saratedd.show_details

import android.os.Bundle
import android.util.Log
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
import com.example.shows_saratedd.ApiModule
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

    var recyclerViewInitialized = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiModule.initRetrofit(requireContext())

        binding.detailsTitle.text = args.showName
        binding.detailsDesc.text = args.showDescription
        Glide
            .with(binding.root)
            .load(args.showImage)
            .placeholder(R.drawable.ic_office)
            .into(binding.detailsImg)

        val email = args.email
        initBackButton(email)


        // u viewmodelu ako je success onda pozvat jednu stvar (successLiveData)
        // ako je failure onda drugu (failurelivedata)
        viewModel.getCreateReviewResultLiveData().observe(viewLifecycleOwner) { createReviewSuccessful ->
            if (createReviewSuccessful) {
            }
        }

        initReviewsRecycler()

        viewModel.loadShowDetails(args.showId)
        viewModel.getShowResultLiveData().observe(viewLifecycleOwner) { showDetailsSuccessful ->
            if (showDetailsSuccessful) {
                binding.detailsProgressBar.isVisible = false
            }
        }
        viewModel.getShowResponseLiveData().observe(viewLifecycleOwner) { showDetails ->
            binding.ratingBar.setRating(showDetails.averageRating)
            binding.detailsData.text =
                showDetails.noOfReviews.toString() + " reviews, " +
                        String.format("%.2f", showDetails.averageRating) + " average"
        }

        viewModel.loadReviews(args.showId)
        viewModel.getReviewsResultLiveData().observe(viewLifecycleOwner) { reviewsSuccessful ->
            if (reviewsSuccessful) {
                recyclerViewInitialized = true
                binding.detailsData.isVisible = true
                binding.ratingBar.isVisible = true
                binding.ratingBar.setIsIndicator(true)
                binding.detailsRecycler.isVisible = true
                binding.detailsReviewsMessage.isVisible = false
            }
        }

        viewModel.getReviewsResponseLiveData().observe(viewLifecycleOwner) { reviews ->
            adapter.getAllReviews(reviews)
        }

        initDialog(email)
    }

    private fun initBackButton(email: String) {
        binding.detailsBackButton.setOnClickListener {
            val directions = ShowDetailsFragmentDirections.toShowsFragment(email)
            findNavController().navigate(directions)
        }
    }

    private fun initDialog(email: String) {
        binding.detailsReviewsButton.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val bottomSheetBinding = DialogAddReviewBinding.inflate(layoutInflater)
            dialog.setContentView(bottomSheetBinding.root)

            bottomSheetBinding.submitButton.setOnClickListener {
                viewModel.onSubmitButtonClicked(
                    args.showId,
                    bottomSheetBinding.dialogRating.getRating().toInt(),
                    bottomSheetBinding.dialogCommentInputEdit.text.toString(),
                )
                viewModel.getCreateReviewResponseLiveData().observe(viewLifecycleOwner) { review ->
                    adapter.addReview(review)
                }
                if (!recyclerViewInitialized) {
                    initReviewsRecycler()
                }

                dialog.dismiss()
                updateRating()
            }
            dialog.show()
        }
    }

    private fun initReviewsRecycler() {
//        recyclerViewInitialized = true
//        binding.detailsData.isVisible = true
//        binding.ratingBar.isVisible = true
//        binding.detailsRecycler.isVisible = true
//        binding.detailsReviewsMessage.isVisible = false

        adapter = ReviewsAdapter(emptyList())
        binding.detailsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        binding.detailsRecycler.adapter = adapter
        binding.detailsRecycler.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun updateRating() {
        viewModel.updateRating(adapter.getReviews())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}