package com.chavez.mobile2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chavez.mobile2.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sample user info (you can replace with real data later)
        binding.tvName.text = "Name: John Doe"
        binding.tvEmail.text = "Email: johndoe@email.com"

        // Back button
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
