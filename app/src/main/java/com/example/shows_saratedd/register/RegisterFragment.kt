package com.example.shows_saratedd.register

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shows_saratedd.ApiModule
import com.example.shows_saratedd.databinding.FragmentRegisterBinding
import com.example.shows_saratedd.login.LoginFragment

class RegisterFragment : Fragment() {
    companion object {
//        const val REGISTRATION = "REGISTRATION"
        const val IS_REGISTRATION = "IS_REGISTRATION"
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: RegistrationViewModel by viewModels()

    private var emailBool = false
    private var passBool = false
    private var repeatPassBool = false
    private var samePass = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(LoginFragment.LOGIN, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiModule.initRetrofit(requireContext())

        viewModel.getRegistrationResultLiveData().observe(viewLifecycleOwner) { registrationSuccessful ->
            displayRegistrationMessage(registrationSuccessful)

            if (registrationSuccessful) {
                sharedPreferences.edit {
                    putBoolean(IS_REGISTRATION, true)
                }

                val directions = RegisterFragmentDirections.toLoginFragment()
                findNavController().navigate(directions)
            }

        }

        initTextListers()
        initRegister()

    }

    private fun initTextListers() {
        binding.emailEditTextField.doAfterTextChanged {
            val regex = Patterns.EMAIL_ADDRESS.toRegex()
            emailBool = regex.matches(binding.emailEditTextField.text.toString())
            binding.registerButton.isEnabled = emailBool && passBool
                    && repeatPassBool && samePass
        }

        binding.passwordEditTextField.doAfterTextChanged {
            passBool = binding.passwordEditTextField.text.toString().length >= 6
            samePass = binding.repeatPasswordEditTextField.text.toString() ==
                    binding.passwordEditTextField.text.toString()
            binding.registerButton.isEnabled = emailBool && passBool
                    && repeatPassBool && samePass
        }

        binding.repeatPasswordEditTextField.doAfterTextChanged {
            repeatPassBool = binding.repeatPasswordEditTextField.text.toString().length >= 6
            samePass = binding.repeatPasswordEditTextField.text.toString() ==
                    binding.passwordEditTextField.text.toString()
            binding.registerButton.isEnabled = emailBool && passBool
                    && repeatPassBool && samePass
        }
    }

    private fun initRegister() {
        binding.registerButton.setOnClickListener {
            viewModel.onRegisterButtonClicked(
                username = binding.emailEditTextField.text.toString(),
                password = binding.passwordEditTextField.text.toString(),
                passConfirmation = binding.repeatPasswordEditTextField.text.toString()
            )
        }
    }

    private fun displayRegistrationMessage(registrationSuccessful: Boolean?) {
        // na neki nacin dati feedback korisniku ako registracija nije uspjela
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//bla