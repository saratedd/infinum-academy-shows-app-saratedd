package com.example.shows_saratedd

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        sharedPreferences = requireContext().getSharedPreferences(REMEMBER_ME, Context.MODE_PRIVATE)
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
//      (un)checkiram remember me s obzirom na to kako je u storageu
        binding.loginRememberMe.isChecked = isRememberMe

        initLister()
//        checkRememberMe()
    }


    private fun initLister() {
        binding.emailEditTextField.doAfterTextChanged {
            val regex = Patterns.EMAIL_ADDRESS.toRegex()
            emailBool = regex.matches(binding.emailEditTextField.text.toString())

            binding.loginButton.isEnabled = emailBool && passBool
        }
        binding.passwordEditTextField.doAfterTextChanged {
            passBool = binding.passwordEditTextField.text.toString().length >= 6
            binding.loginButton.isEnabled = emailBool && passBool
        }
//      mijenjam u storageu jel remember me (un)checkan s obzirom na novi input
        binding.loginRememberMe.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit {
                putBoolean(IS_REMEMBER_ME, isChecked)
            }
        }

        binding.loginButton.setOnClickListener {
//            val intent = Intent(this, ShowsActivity::class.java)
//            intent.putExtra(
//                EXTRA_EMAIL,
//                binding.emailEditTextField.text.toString().substringBefore("@", "")
//            )
//            startActivity(intent)
//            u useru je samo dio prije @
            var user = binding.emailEditTextField.text.toString().substringBefore("@", "")
            var directions = LoginFragmentDirections.toShowsFragment(user)
            findNavController().navigate(directions)
        }
    }

    private fun checkRememberMe() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//bla