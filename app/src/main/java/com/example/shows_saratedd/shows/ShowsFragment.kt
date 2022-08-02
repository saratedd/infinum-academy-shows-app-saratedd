package com.example.shows_saratedd.shows

import android.app.AlertDialog
import android.content.*
import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import com.example.shows_saratedd.*
import com.example.shows_saratedd.login.LoginFragment.Companion.LOGIN
import com.example.shows_saratedd.databinding.DialogUserBinding
import com.example.shows_saratedd.databinding.FragmentShowsBinding
import com.example.shows_saratedd.db.ShowEntity
import com.example.shows_saratedd.db.ShowsDatabase
import com.example.shows_saratedd.db.ShowsViewModelFactory
import com.example.shows_saratedd.login.LoginFragment
import com.example.shows_saratedd.register.RegisterFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class ShowsFragment : Fragment() {
    companion object {
        const val EXTRA_SHOW_NAME = "showName"
        const val EXTRA_SHOW_DESC = "showDesc"
        const val EXTRA_SHOW_IMAGE = "showImage"
        const val EXTRA_USER = "user"
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    private lateinit var adapter: ShowsAdapter
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!

    private lateinit var getCameraImage: ActivityResultLauncher<Uri>
    lateinit var uri: Uri

    private val args by navArgs<ShowsFragmentArgs>()
    private val viewModel: ShowsViewModel by viewModels {
        ShowsViewModelFactory((activity?.application as ShowsApplication).database)
    }


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
                viewModel.updateUserProfilePhoto(requireContext())
            }
        }
        sharedPreferences = requireContext().getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiModule.initRetrofit(requireContext())

        super.onViewCreated(view, savedInstanceState)
        val email = args.email

        initShowsRecycler(email)

        loadImage(binding.showsUser, uri)

        initUserButton(email)
        initLoadShowsButton()
    }

    private fun initShowsRecycler(email: String) {

        adapter = ShowsAdapter(emptyList()) { show ->
            val directions = ShowsFragmentDirections.toShowDetailsFragment(
//                show.name, show.description, show.imageResourceID, email
//                show.title, show.description, R.drawable.ic_office, email
                show.title, show.description, show.imageUrl, email, show.id
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

            loadImage(bottomSheetBinding.userPicture, uri)

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

        builder?.setPositiveButton(R.string.logout) { _, _ ->
            sharedPreferences.edit {
                putString(LoginFragment.USER, null)
                putBoolean(LoginFragment.IS_REMEMBER_ME, false)
                putBoolean(RegisterFragment.IS_REGISTRATION, false)
            }
            val directions = ShowsFragmentDirections.toLoginFragment()
            findNavController().navigate(directions)
        }
        builder?.setNegativeButton(R.string.cancel) { _, _ -> }

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

            if (InternetConnectionUtil.checkInternetConnection(requireContext())) {
                binding.showsProgressBar.isVisible = true

                viewModel.onLoadShowsButtonClicked()
                // u viewmodelu ako je success onda pozvat jednu stvar (successLiveData)
                // ako je failure onda drugu (failurelivedata)
                viewModel.getShowsResultLiveData().observe(viewLifecycleOwner) { showsSuccessful ->
                    if (showsSuccessful) {
                        binding.showsProgressBar.isVisible = false
                    }
                }
                viewModel.getShowsLiveData().observe(viewLifecycleOwner) { shows ->
                    adapter.addAllShows(shows)
                    viewModel.insertAllShowsToDB(shows.map { show ->
                        ShowEntity(
                            show.id,
                            show.averageRating,
                            show.description,
                            show.imageUrl,
                            show.noOfReviews,
                            show.title
                        )
                    })
                }

                showUI()

            } else {
                // 'no internet connection' toast
//                val database: ShowsDatabase
//
//                if (database.showDao().getAllShows() == null) {
//
//                }
                viewModel.getShowsFromDB().observe(viewLifecycleOwner) { showEntities ->
                    adapter.addAllShows(showEntities.map { showEntity ->
                        Show(
                            showEntity.id,
                            showEntity.averageRating,
                            showEntity.description,
                            showEntity.imageUrl,
                            showEntity.noOfReviews,
                            showEntity.title
                        )
                    })
                }
                showUI()
            }
        }
    }

    private fun showUI() {
        binding.showsRecycler.isVisible = true
        binding.emptyStateIcon.isVisible = false
        binding.emptyStateText.isVisible = false
        binding.loadButton.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}