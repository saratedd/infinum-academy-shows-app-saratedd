package com.example.shows_saratedd

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
//import com.example.shows_saratedd.databinding.ActivityLoginBinding
import com.example.shows_saratedd.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    companion object {
        const val EXTRA_EMAIL = "email"
        const val REMEMBER_ME = "remember_me"
        const val IS_REMEMBER_ME = "IS_REMEMBER_ME"
        const val USER = "USER"
        const val PICTURE = "PICTURE"
    }
//zasto nam treba ovo ? = null
//    !! sta?
//    _binding i binding difference?
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    private var emailBool = false
    private var passBool = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(REMEMBER_ME, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isRememberMe = sharedPreferences.getBoolean(IS_REMEMBER_ME, false)
        val isUser = sharedPreferences.getString(USER, null)

        if (isUser != null && isRememberMe) {
            val directions = LoginFragmentDirections.toShowsFragment(isUser)
            findNavController().navigate(directions)
        }
        initTextListers()
        initRememberMe()
        initLogin()

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


    private fun initLogin() {
        binding.loginButton.setOnClickListener {
            val user = binding.emailEditTextField.text.toString()//.substringBefore("@", "")
            sharedPreferences.edit {
                putString(USER, user)
                putBoolean(PICTURE, false)
            }

            val directions = LoginFragmentDirections.toShowsFragment(user)
            findNavController().navigate(directions)
        }
    }

    private fun initRememberMe() {
        binding.loginRememberMe.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit {
                putBoolean(IS_REMEMBER_ME, isChecked)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//bla