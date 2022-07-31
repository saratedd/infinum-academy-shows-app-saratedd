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
//    zasto je negdje : a negdje =
//    private lateinit var binding: ActivityShowsBinding
    private lateinit var adapter: ShowsAdapter
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ShowsFragmentArgs>()
    private val viewModel by viewModels<ShowsViewModel>()

    private lateinit var sharedPreferences: SharedPreferences

//    private val shows = listOf(
////        Show("0",
////            "The Office",
////            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
////            R.drawable.ic_office),
////        Show("1",
////            "Stranger Things",
////            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
////            R.drawable.ic_stranger_things),
////        Show("2",
////            "Krv nije voda",
////            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
////            R.drawable.krv_nije_voda)
//        Show(
//            "The Office",
//            "The Office is an American mockumentary sitcom television series that depicts the everyday work lives of office employees in the Scranton, Pennsylvania, branch of the fictional Dunder Mifflin Paper Company. It aired on NBC from March 24, 2005, to May 16, 2013, lasting a total of nine seasons.",
//            R.drawable.ic_office
//        ),
//        Show(
//            "Stranger Things",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non rutrum felis. Quisque dignissim tellus a velit vehicula, non malesuada lorem eleifend. Maecenas vitae varius metus, a mollis sem. Mauris ut urna nulla. Suspendisse eget magna in ex luctus porttitor sit amet id odio. Cras in tincidunt erat, sed rutrum erat. Integer mattis, turpis id suscipit vestibulum, neque justo venenatis ligula, maximus auctor augue urna ut ipsum.",
//            R.drawable.ic_stranger_things
//        ),
//        Show(
//            "Krv nije voda",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam non rutrum felis. Quisque dignissim tellus a velit vehicula, non malesuada lorem eleifend. Maecenas vitae varius metus, a mollis sem. Mauris ut urna nulla. Suspendisse eget magna in ex luctus porttitor sit amet id odio. Cras in tincidunt erat, sed rutrum erat. Integer mattis, turpis id suscipit vestibulum, neque justo venenatis ligula, maximus auctor augue urna ut ipsum.",
//            R.drawable.krv_nije_voda
//        )
//    )

    //    lateinit = declaring a property/variable wo definition


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        layout inflation = xml -> kotlin/java code
//        binding = ActivityShowsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        var user = intent.extras?.getString(LoginFragment.EXTRA_EMAIL)
//----
//        if (user != null) {
//            initShowsRecycler(user)
//        } else initShowsRecycler("anoniman")
//        initLoadShowsButton()
//---
//        binding.showsLogout.setOnClickListener {
//            var intent = Intent(this, LoginFragment::class.java)
//            startActivity(intent)
//        }
////        initEmptyStateButton()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        sto je 'attachToParent'
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
//    zasto vracamo binding a ne _binding (koja je razlika uopce)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiModule.initRetrofit(requireContext())
//        layout inflation = xml -> kotlin/java code
//        binding = ActivityShowsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        var user = intent.extras?.getString(LoginFragment.EXTRA_EMAIL)
        super.onViewCreated(view, savedInstanceState)
        val email = args.email
//        val user = email.substringBefore("@", "")
//        if (user != null) {
//            initShowsRecycler(user)
//        } else initShowsRecycler("anoniman")
//        initLoadShowsButton()
//        maknut if?
        if (email != null)
            initShowsRecycler(email)
        else
            initShowsRecycler("anoniman")

        initUserButton(email)
        initLoadShowsButton()
    }



    private fun initShowsRecycler(email: String) {
//        onitemclickcallback
        adapter = ShowsAdapter(emptyList()) { show ->
//            val intent = Intent(this, ShowDetailsActivity::class.java)
//            intent.putExtra(EXTRA_SHOW_NAME, show.name)
//            intent.putExtra(EXTRA_SHOW_DESC, show.description)
//            intent.putExtra(EXTRA_SHOW_IMAGE, show.imageResourceID)
//            intent.putExtra(EXTRA_USER, user)
//            startActivity(intent)
            var directions = ShowsFragmentDirections.toShowDetailsFragment(
//                show.name, show.description, show.imageResourceID, email
                show.title, show.description, R.drawable.ic_office, email
            )
            findNavController().navigate(directions)

        }
//        binding.showsRecycler.layoutManager = LinearLayoutManager(this)
//        binding.showsRecycler.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        binding.showsRecycler.adapter = adapter
//        binding.showsRecycler.addItemDecoration(
//            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        )
        binding.showsRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.showsRecycler.adapter = adapter
        binding.showsRecycler.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }
    private fun initUserButton(email: String) {
        binding.showsUser.setOnClickListener {
//      var intent = Intent(this, LoginFragment::class.java)
//      startActivity(intent)
            val dialog = BottomSheetDialog(requireContext())
            val bottomSheetBinding = DialogUserBinding.inflate(layoutInflater)
            dialog.setContentView(bottomSheetBinding.root)

            bottomSheetBinding.userEmail.text = email

            bottomSheetBinding.userChangePicture.setOnClickListener {
                dispatchTakePictureIntent()
                TODO()
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
        builder?.setPositiveButton(R.string.logout) { _, _ ->
//            LoginFragment.sharedPreferences.edit {
//                putBoolean(LoginFragment.IS_REMEMBER_ME, isChecked)
//            }
            sharedPreferences.edit {
                putString(LoginFragment.USER, null)
                putBoolean(LoginFragment.IS_REMEMBER_ME, false)
                putBoolean(RegisterFragment.IS_REGISTRATION, false)
            }

            val directions = ShowsFragmentDirections.toLoginFragment()
            findNavController().navigate(directions)
        }
        builder?.setNegativeButton(R.string.cancel) { _, _ ->
            // nista
        }
        val alertDialog : AlertDialog? = builder?.create()
        alertDialog?.show()
    }

    private fun dispatchTakePictureIntent() {
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        try {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//        } catch (e: ActivityNotFoundException) {
//            // display error state to the user
//        }
    }

    private fun initLoadShowsButton() {
        binding.loadButton.setOnClickListener {
//            adapter.addAllShows(shows)
//            viewModel.showsLiveData.observe(viewLifecycleOwner) { shows ->
//                adapter.addAllShows(shows)
//            }
            viewModel.onLoadShowsButtonClicked()
            viewModel.getShowsResultLiveData().observe(viewLifecycleOwner) { showsSuccessful ->

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