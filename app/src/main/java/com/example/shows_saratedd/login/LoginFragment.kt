package com.example.shows_saratedd.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shows_saratedd.R
import com.example.shows_saratedd.register.RegisterFragment
//import com.example.shows_saratedd.databinding.ActivityLoginBinding
import com.example.shows_saratedd.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    companion object {
        const val LOGIN = "LOGIN"
        const val IS_REMEMBER_ME = "IS_REMEMBER_ME"
        const val USER = "USER"
    }
//zasto nam treba ovo ? = null
//    !! sta?
//    _binding i binding difference?
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences



    var emailBool = false
    var passBool = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        var emailBool = false
//        var passBool = false
//
//
//        binding.emailEditTextField.doAfterTextChanged {
//            val regex = Patterns.EMAIL_ADDRESS.toRegex()
//            emailBool = regex.matches(binding.emailEditTextField.text.toString())
//
//            binding.loginButton.isEnabled = emailBool && passBool
//        }
//        binding.passwordEditTextField.doAfterTextChanged {
//            passBool = binding.passwordEditTextField.text.toString().length >= 6
//            binding.loginButton.isEnabled = emailBool && passBool
//        }
//
//        binding.loginButton.setOnClickListener {
//            val intent = Intent(this, ShowsActivity::class.java)
//            intent.putExtra(
//                EXTRA_EMAIL,
//                binding.emailEditTextField.text.toString().substringBefore("@", "")
//            )
//            startActivity(intent)
//        }
//
//    }
//    sta se uopce radi u onCreateView kad tad jos nemam nista
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
//      binding = ActivityLoginBinding.inflate(layoutInflater)
//      setContentView(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//      uzimam boolean iz storagea, zas se ne mijenja stavila ovdje true il false, cemu sluzi to
        val isRememberMe = sharedPreferences.getBoolean(IS_REMEMBER_ME, false)
        val isUser = sharedPreferences.getString(USER, null)
        val isRegistration = sharedPreferences.getBoolean(RegisterFragment.IS_REGISTRATION, false)
        if (isRegistration) {
            binding.loginText.text = getString(R.string.registration_successful)
            binding.registerButton.isVisible = false
        }

//      (un)checkiram remember me s obzirom na to kako je u storageu
        binding.loginRememberMe.isChecked = isRememberMe
        if (isUser != null && isRememberMe) {
            var directions = LoginFragmentDirections.toShowsFragment(isUser)
            findNavController().navigate(directions)
        }
        initTextListers()
        initLogin()
        initRegisterListener()

    }


    private fun initTextListers() {
        binding.emailEditTextField.doAfterTextChanged {
            val regex = Patterns.EMAIL_ADDRESS.toRegex()
            emailBool = regex.matches(binding.emailEditTextField.text.toString())
            binding.loginButton.isEnabled = emailBool && passBool
        }

        binding.passwordEditTextField.doAfterTextChanged {
            passBool = binding.passwordEditTextField.text.toString().length >= 6
            binding.loginButton.isEnabled = emailBool && passBool
        }
    }

    private fun initRegisterListener() {
        binding.registerButton.setOnClickListener {
            var directions = LoginFragmentDirections.toRegisterFragment()
            findNavController().navigate(directions)
        }
    }

    private fun initLogin() {
        binding.loginButton.setOnClickListener {
//            val intent = Intent(this, ShowsActivity::class.java)
//            intent.putExtra(
//                EXTRA_EMAIL,
//                binding.emailEditTextField.text.toString().substringBefore("@", "")
//            )
//            startActivity(intent)
            var user = binding.emailEditTextField.text.toString()//.substringBefore("@", "")
            initRememberMe(user)
            sharedPreferences.edit {
                putString(USER, user)
            }

            var directions = LoginFragmentDirections.toShowsFragment(user)
            findNavController().navigate(directions)
        }
    }

    private fun initRememberMe(user : String) {
        //      mijenjam u storageu jel remember me (un)checkan s obzirom na novi input
        binding.loginRememberMe.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit {
                putBoolean(IS_REMEMBER_ME, isChecked)
//                putString(USER, user)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//bla