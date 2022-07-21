package com.example.shows_saratedd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
//import com.example.shows_saratedd.databinding.ActivityLoginBinding
import com.example.shows_saratedd.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    companion object {
        const val EXTRA_EMAIL = "email"
    }
//zasto nam treba ovo ? = null
//    !! sta?
//    _binding i binding difference?
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    var emailBool = false
    var passBool = false
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

        initLister()
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//bla