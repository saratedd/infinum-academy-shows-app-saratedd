package com.example.shows_saratedd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shows_saratedd.databinding.ActivityLoginBinding
import com.example.shows_saratedd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var email = ""
        if (binding.emailEditTextField.text.toString().contains("@")) {
            email = binding.emailEditTextField.text.toString().substringBefore("@", "")
            binding.loginButton.isEnabled = true

            binding.loginButton.setOnClickListener {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
            }
        }



    }
}