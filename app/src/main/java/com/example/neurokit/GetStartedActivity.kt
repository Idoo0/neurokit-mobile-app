package com.example.neurokit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GetStartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This line connects the Kotlin file to the XML layout file
        setContentView(R.layout.activity_get_started)

        // Find the buttons in the layout by their ID
        val getStartedButton: Button = findViewById(R.id.getStartedButton)
        val skipButton: TextView = findViewById(R.id.skipButton)

        // Set a click listener for the "Get Started" button
        getStartedButton.setOnClickListener {
            // Create an Intent to open your MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Finish this activity so the user can't go back to it
            finish()
        }

        // Set a click listener for the "Skip" button
        skipButton.setOnClickListener {
            // You can have this do the same thing or something different
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
