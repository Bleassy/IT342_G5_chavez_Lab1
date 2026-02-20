package com.chavez.mobile2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chavez.mobile2.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set welcome message
        binding.tvWelcome.text = "Welcome! You are logged in."

        // Profile button click
        binding.btnProfile.setOnClickListener {
            // Open ProfileActivity (make sure you have this activity)
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Logout button click
        binding.btnLogout.setOnClickListener {
            // Clear session / preferences if needed
            // Example: SharedPreferences clear logic here
            // For now, just show a Toast and go back to Login
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
