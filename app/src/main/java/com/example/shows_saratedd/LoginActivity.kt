package com.example.shows_saratedd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.example.shows_saratedd.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.loginButton.setOnClickListener {
//            val intent = Intent(this, WelcomeActivity::class.java)
//            intent.putExtra("email", binding.emailEditTextField.text.toString())
//            startActivity(intent)
//        }
        var email = binding.emailEditTextField.text.toString()
        var pass = binding.passwordEditTextField.text.toString()
        var emailBool = false
        var passBool = false
        val intent = Intent(this, WelcomeActivity::class.java)

//        binding.emailEditTextField.doAfterTextChanged {
////            var email = binding.emailEditTextField.text.toString()
//            val regex = Regex(".@")
//
//            if (regex.matches(email)) {
////                email = email.substringBefore("@", "")
//                emailBool = true
//            }
//        }
//
//
//        binding.passwordEditTextField.doAfterTextChanged {
//            if (pass.length >= 6) {
//                passBool = true
//            }
//        }
//
//        if (emailBool && passBool) {
//            binding.loginButton.setOnClickListener {
//                val intent = Intent(this, WelcomeActivity::class.java)
//                intent.putExtra("email", email.substringBefore("@", ""))
//                startActivity(intent)
//            }
//        }

//        val textWatcher = object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//            }
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//        }

        binding.emailEditTextField.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                binding.loginButton.setOnClickListener {
////                    val intent = Intent(this, WelcomeActivity::class.java)
//                    intent.putExtra("email", s.toString().substringBefore("@", ""))
//                    startActivity(intent)
//                }
                val regex = Regex(".@")
                emailBool = regex.matches(s.toString())
            }
        })
        binding.passwordEditTextField.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                binding.loginButton.setOnClickListener {
////                    val intent = Intent(this, WelcomeActivity::class.java)
//                    intent.putExtra("email", s.toString().substringBefore("@", ""))
//                    startActivity(intent)
//                }
                passBool = s.toString().length >= 6
            }
        })

        if (emailBool && passBool) {
            binding.loginButton.setOnClickListener {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("email", binding.emailEditTextField.text.toString().substringBefore("@", ""))
                startActivity(intent)
            }
        }

    }
}