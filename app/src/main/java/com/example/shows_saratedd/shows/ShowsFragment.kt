package com.example.shows_saratedd.shows

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shows_saratedd.ApiModule
import com.example.shows_saratedd.R
import com.example.shows_saratedd.login.LoginFragment.Companion.LOGIN
import com.example.shows_saratedd.databinding.DialogUserBinding
import com.example.shows_saratedd.databinding.FragmentShowsBinding
import com.example.shows_saratedd.login.LoginFragment
import com.example.shows_saratedd.register.RegisterFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.example.shows_saratedd.databinding.ActivityShowsBinding

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

    private val args by navArgs<ShowsFragmentArgs>()
    private val viewModel by viewModels<ShowsViewModel>()

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        initUserButton(email)
        initLoadShowsButton()
    }



    private fun initShowsRecycler(email: String) {

        adapter = ShowsAdapter(emptyList()) { show ->
            val directions = ShowsFragmentDirections.toShowDetailsFragment(
//                show.name, show.description, show.imageResourceID, email
                show.title, show.description, R.drawable.ic_office, email
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
                // kamera, assignment5
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

    private fun initLoadShowsButton() {
        binding.loadButton.setOnClickListener {
            viewModel.onLoadShowsButtonClicked()
            viewModel.getShowsResultLiveData().observe(viewLifecycleOwner) { showsSuccessful ->
                if (showsSuccessful) {
                    viewModel.getShowsLiveData().observe(viewLifecycleOwner) { shows ->
                        adapter.addAllShows(shows)
                    }
                }
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