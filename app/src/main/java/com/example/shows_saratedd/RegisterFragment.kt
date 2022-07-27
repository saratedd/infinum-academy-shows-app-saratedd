package com.example.shows_saratedd

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
import androidx.navigation.fragment.findNavController
import com.example.shows_saratedd.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    companion object {
        const val REGISTRATION = "REGISTRATION"
        const val IS_REGISTRATION = "IS_REGISTRATION"
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

//    private lateinit var sharedPreferences: SharedPreferences

    private var emailBool = false
    private var passBool = false
    private var repeatPassBool = false
    private var samePass = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        sharedPreferences = requireContext().getSharedPreferences(REGISTRATION, Context.MODE_PRIVATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val isRegistration = sharedPreferences.getBoolean(IS_REGISTRATION, true)

//        binding.loginRememberMe.isChecked = isRememberMe
//        if (isUser != null && isRememberMe) {
//            var directions = LoginFragmentDirections.toShowsFragment(isUser)
//            findNavController().navigate(directions)
//        }
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
//
//            sharedPreferences.edit {
//                putBoolean(IS_REGISTRATION, true)
//            }

            var directions = RegisterFragmentDirections.toLoginFragment(true)
            findNavController().navigate(directions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//bla