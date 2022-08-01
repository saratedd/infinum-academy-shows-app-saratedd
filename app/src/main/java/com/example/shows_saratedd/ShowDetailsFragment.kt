package com.example.shows_saratedd

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.shows_saratedd.databinding.ActivityShowDetailsBinding
import com.example.shows_saratedd.databinding.DialogAddReviewBinding
import com.example.shows_saratedd.databinding.FragmentShowDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import show.Review
import show.ReviewsAdapter

class ShowDetailsFragment : Fragment() {
//    lateinit var binding: ActivityShowDetailsBinding
    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: ReviewsAdapter

    private val args by navArgs<ShowDetailsFragmentArgs>()

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
        binding.detailsImg.setImageResource(args.showImage)

        val user = args.username
        initBackButton(user)

//        maknut if?
        if (user != null) {
            initDialog(user)
        } else {
            initDialog("anoniman")
        }
    }

    private fun initBackButton(user: String) {
        binding.detailsBackButton.setOnClickListener {
//            val intentBack = Intent(this, ShowsFragment::class.java)
//            startActivity(intentBack)
//            var directions = ShowDetailsFragmentDirections.toShowsFragment(user)
//            findNavController().navigate(directions)
            findNavController().popBackStack()
        }
    }


    private fun initDialog(user: String) {
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
                    user,
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