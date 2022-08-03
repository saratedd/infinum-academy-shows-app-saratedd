package com.example.shows_saratedd.show_details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.shows_saratedd.*
//import com.example.shows_saratedd.databinding.ActivityShowDetailsBinding
import com.example.shows_saratedd.databinding.DialogAddReviewBinding
import com.example.shows_saratedd.databinding.FragmentShowDetailsBinding
import com.example.shows_saratedd.db.ReviewEntity
import com.example.shows_saratedd.db.ShowDetailsViewModelFactory
import com.example.shows_saratedd.db.ShowsViewModelFactory
import com.example.shows_saratedd.shows.ShowsFragment
import com.example.shows_saratedd.shows.ShowsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class ShowDetailsFragment : Fragment() {

    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: ReviewsAdapter
    lateinit var uri: Uri

    private val args by navArgs<ShowDetailsFragmentArgs>()
    private val viewModel: ShowDetailsViewModel by viewModels {
        ShowDetailsViewModelFactory((activity?.application as ShowsApplication).database)
    }

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

        viewModel.getCreateReviewResultLiveData().observe(viewLifecycleOwner) { createReviewSuccessful ->
            if (createReviewSuccessful) {
            }
        }

        initReviewsRecycler()

        if (InternetConnectionUtil.checkInternetConnection(requireContext())) {
            viewModel.loadShowDetails(args.showId)
            viewModel.getShowResultLiveData().observe(viewLifecycleOwner) { showDetailsSuccessful ->
                if (showDetailsSuccessful) {
                    binding.detailsProgressBar.isVisible = false
                }
            }
            viewModel.getShowResponseLiveData().observe(viewLifecycleOwner) { showDetails ->
    //                setShowUI(showDetails.averageRating, showDetails.noOfReviews)
                binding.ratingBar.setRating(showDetails.averageRating)
                binding.detailsData.text =
                    showDetails.noOfReviews.toString() + " reviews, " +
                            String.format("%.2f", showDetails.averageRating) + " average"
            }

            viewModel.loadReviews(args.showId)
            viewModel.getReviewsResultLiveData().observe(viewLifecycleOwner) { reviewsSuccessful ->
                if (reviewsSuccessful) {
                    setReviewsUI()
                }
            }

            viewModel.getReviewsResponseLiveData().observe(viewLifecycleOwner) { reviews ->
                adapter.getAllReviews(reviews)
                viewModel.insertAllReviewsToDB(reviews.map { review ->
                    ReviewEntity(
                        review.id,
                        review.comment,
                        review.rating,
                        review.showId,
                        review.user.id,
                        review.user.email,
                        review.user.imageUrl
                    )
                })
            }

        } else {
            binding.detailsProgressBar.isVisible = false
            viewModel.getShowFromDB(args.showId).observe(viewLifecycleOwner) { showEntity ->
                setShowUI(showEntity.averageRating, showEntity.noOfReviews)
            }
            viewModel.getAllReviewsFromDB(args.showId.toInt()).observe(viewLifecycleOwner) { reviewEntities ->
                adapter.getAllReviews(reviewEntities.map { reviewEntity ->
                    Review(
                        reviewEntity.id,
                        reviewEntity.comment,
                        reviewEntity.rating,
                        reviewEntity.showId,
                        User(reviewEntity.userId, reviewEntity.email, reviewEntity.userImageUrl)
                    )
                })
            }
            setReviewsUI()

        }
//         u viewmodelu ako je success onda pozvat jednu stvar (successLiveData)
//         ako je failure onda drugu (failurelivedata)
        viewModel.getCreateReviewResultLiveData().observe(viewLifecycleOwner) { createReviewSuccessful ->
            if (createReviewSuccessful) {
            }
        }

        initDialog(email)
    }

    private fun setShowUI(averageRating: Float, noOfReviews: Int) {
        binding.ratingBar.setRating(averageRating)
        binding.detailsData.text =
            noOfReviews.toString() + " reviews, " +
                    String.format("%.2f", averageRating) + " average"
    }

    private fun setReviewsUI() {
        recyclerViewInitialized = true
        binding.detailsData.isVisible = true
        binding.ratingBar.isVisible = true
        binding.ratingBar.setIsIndicator(true)
        binding.detailsRecycler.isVisible = true
        binding.detailsReviewsMessage.isVisible = false
    }

    private fun initBackButton(email: String) {
        binding.detailsBackButton.setOnClickListener {
            val directions = ShowDetailsFragmentDirections.toShowsFragment(email)
            findNavController().navigate(directions)
        }
    }

    private fun initDialog(email: String) {
        binding.detailsReviewsButton.setOnClickListener {
            if (InternetConnectionUtil.checkInternetConnection(requireContext())) {
                val dialog = BottomSheetDialog(requireContext())
                val bottomSheetBinding = DialogAddReviewBinding.inflate(layoutInflater)
                dialog.setContentView(bottomSheetBinding.root)

                val file = FileUtil.getImageFile(requireContext())
                file?.let {
                    uri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", it)
                }

                bottomSheetBinding.submitButton.setOnClickListener {

                    viewModel.onSubmitButtonClicked(
                        args.showId,
                        bottomSheetBinding.dialogRating.getRating().toInt(),
                        bottomSheetBinding.dialogCommentInputEdit.text.toString(),
                    )
                    viewModel.getCreateReviewResponseLiveData().observe(viewLifecycleOwner) { review ->
                        adapter.addReview(review)
                        viewModel.insertReviewToDB(
                            ReviewEntity(
                            review.id,
                            review.comment,
                            review.rating,
                            review.showId,
                            review.user.id,
                            review.user.email,
                            review.user.imageUrl
                        ))
                    }
                    if (!recyclerViewInitialized) {
                        initReviewsRecycler()
                    }

                    dialog.dismiss()
                    updateRating()
                }
                dialog.show()
            } else {
//                Toast.makeText(requireContext(), getString(R.string.no_net_reviews), Toast.LENGTH_LONG).show()
//                Toast.makeText(requireContext(), getString(R.string.no_net), Toast.LENGTH_LONG).show()
                // ako uzimam iz strings.xml onda mi izbaci 'write a review' kao tekst (uncomment linija 205)
                Toast.makeText(requireContext(), "There is no internet connection. Please check your internet connection and try again.", Toast.LENGTH_LONG).show()
            }
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

    private fun loadImage(view: ImageView, url: Uri?) {
        Glide
            .with(requireContext())
            .load(url)
            .placeholder(R.drawable.ic_profile_placeholder)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerCrop()
            .into(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
