package com.example.shows_saratedd.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shows_saratedd.ApiModule
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

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: LoginViewModel by viewModels()

    var emailBool = false
    var passBool = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
        ApiModule.initRetrofit(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateShowIcon()
        animateShowText()

        val isRememberMe = sharedPreferences.getBoolean(IS_REMEMBER_ME, false)
        val isUser = sharedPreferences.getString(USER, null)
        val isRegistration = sharedPreferences.getBoolean(RegisterFragment.IS_REGISTRATION, false)

        if (isRegistration) {
            binding.loginText.text = getString(R.string.registration_successful)
            binding.registerButton.isVisible = false
        }

        if (isUser != null && isRememberMe) {
            var directions = LoginFragmentDirections.toShowsFragment(isUser)
            findNavController().navigate(directions)
        }

        initTextListeners()
        initRememberMe()
        initLogin()
        initRegisterListener()
        initObserveLogin()

    }

    private fun initTextListeners() {
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
            viewModel.onLoginButtonClicked(
                username = binding.emailEditTextField.text.toString(),
                password = binding.passwordEditTextField.text.toString(),
                sharedPreferences
            )
        }
    }

    private fun initObserveLogin() {
        viewModel.getLoginResultLiveData().observe(viewLifecycleOwner) { loginSuccessful ->
            if (loginSuccessful) {
                val user = binding.emailEditTextField.text.toString()//.substringBefore("@", "")
                sharedPreferences.edit {
                    putString(USER, user)
                }

                val directions = LoginFragmentDirections.toShowsFragment(user)
                findNavController().navigate(directions)

            } else {
                // dati feedback korisniku
            }
        }
    }

    private fun initRememberMe() {
        binding.loginRememberMe.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit {
                putBoolean(IS_REMEMBER_ME, isChecked)
            }
        }
    }

    private fun animateShowIcon() {
        binding.logoImage
            .animate()
            .translationY(0f)
            .setDuration(700)
            .setStartDelay(500)
            .setInterpolator(OvershootInterpolator()).start()
    }

    private fun animateShowText() {
        binding.logoText
            .animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .setStartDelay(1200)
            .setInterpolator(BounceInterpolator()).start()

//            .translationZ(0f)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}