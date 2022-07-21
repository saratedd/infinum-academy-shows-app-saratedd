package com.example.shows_saratedd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.example.shows_saratedd.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_EMAIL = "email"
    }

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var emailBool = false
        var passBool = false


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
            val intent = Intent(this, ShowsActivity::class.java)
            intent.putExtra(
                EXTRA_EMAIL,
                binding.emailEditTextField.text.toString().substringBefore("@", "")
            )
            startActivity(intent)
        }

    }
}
//bla