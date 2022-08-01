package com.example.shows_saratedd

import android.app.AlertDialog
import android.content.*
import android.content.ContentValues.TAG
import android.net.Uri
import show.Show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.shows_saratedd.LoginFragment.Companion.REMEMBER_ME
import com.example.shows_saratedd.databinding.DialogUserBinding
import com.example.shows_saratedd.databinding.FragmentShowsBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.example.shows_saratedd.databinding.ActivityShowsBinding
import show.ShowsAdapter
import java.io.File

class ShowsFragment : Fragment() {
    companion object {
        const val EXTRA_SHOW_NAME = "showName"
        const val EXTRA_SHOW_DESC = "showDesc"
        const val EXTRA_SHOW_IMAGE = "showImage"
        const val EXTRA_USER = "user"
        const val REQUEST_IMAGE_CAPTURE = 1
    }
//    zasto je negdje : a negdje =
//    private lateinit var binding: ActivityShowsBinding
    private lateinit var adapter: ShowsAdapter
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!

    private lateinit var getCameraImage: ActivityResultLauncher<Uri>
    lateinit var uri: Uri

    private val args by navArgs<ShowsFragmentArgs>()
    private val viewModel by viewModels<ShowsViewModel>()

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val file = FileUtil.createImageFile(requireContext())
        file?.let {
            uri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", it)
        }
        getCameraImage = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                Log.i(TAG, "Got image at: $uri")
                loadImage(binding.showsUser, uri)
                loadImage(binding.)
            }
        }
        sharedPreferences = requireContext().getSharedPreferences(REMEMBER_ME, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = args.email
        if (email != null)
            initShowsRecycler(email)
        else
            initShowsRecycler("anoniman")

        initUserButton(email)
        initLoadShowsButton()
    }


    private fun initShowsRecycler(email: String) {
        adapter = ShowsAdapter(emptyList()) { show ->
            var directions = ShowsFragmentDirections.toShowDetailsFragment(
                show.name, show.description, show.imageResourceID, email
            )
            findNavController().navigate(directions)

        }
        binding.showsRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.showsRecycler.adapter = adapter
        binding.showsRecycler.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }
    private fun initUserButton(email: String) {
        binding.showsUser.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val bottomSheetBinding = DialogUserBinding.inflate(layoutInflater)
            dialog.setContentView(bottomSheetBinding.root)

            bottomSheetBinding.userEmail.text = email

            bottomSheetBinding.userChangePicture.setOnClickListener {
                getCameraImage.launch(uri)
                dialog.dismiss()
            }

            bottomSheetBinding.userLogout.setOnClickListener {
                initAlertDialog()
                dialog.dismiss()
            }

            dialog.show()

        }
    }

    private fun initAlertDialog() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.setMessage(R.string.logout_alert_message)
//        builder?.setPositiveButton(R.string.logout, object : DialogInterface.OnClickListener {
//            override fun onClick(p0: DialogInterface?, p1: Int) {
//            }
//        })
        builder?.setPositiveButton(R.string.logout) { p0, p1 ->
            sharedPreferences.edit {
                putString(LoginFragment.USER, null)
                putBoolean(LoginFragment.IS_REMEMBER_ME, false)
            }
            var directions = ShowsFragmentDirections.toLoginFragment()
            findNavController().navigate(directions)
        }
        builder?.setNegativeButton(R.string.cancel) { p0, p1 -> }

        val alertDialog : AlertDialog? = builder?.create()
        alertDialog?.show()
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

    private fun initLoadShowsButton() {
        binding.loadButton.setOnClickListener {
            viewModel.showsLiveData.observe(viewLifecycleOwner) { shows ->
                adapter.addAllShows(shows)
            }
            binding.showsRecycler.isVisible = true
            binding.emptyStateIcon.isVisible = false
            binding.emptyStateText.isVisible = false
            binding.loadButton.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}