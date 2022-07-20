package com.example.shows_saratedd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shows_saratedd.databinding.ActivityWelcomeBinding


class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.logedInUser.text = "Welcome, " + intent.extras?.getString("email") + "!"
    }

}
