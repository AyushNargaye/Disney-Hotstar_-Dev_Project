package com.example.splashscreen

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Retrieve the name, email, and password from the intent extras
        val name = intent.getStringExtra(SignInActivity.KEY1)
        val email = intent.getStringExtra(SignInActivity.KEY2)
        val pass = intent.getStringExtra(SignInActivity.KEY3)

        // UI elements to show user information
        val welcomeText = findViewById<TextView>(R.id.tWelcome)
        val btnMail = findViewById<TextView>(R.id.btnMail)
        val btnPass = findViewById<TextView>(R.id.btnPass)

        // Set the user information to the respective TextViews
        welcomeText.text = "Welcome, $name!"
        btnMail.text = "Email: $email"
        btnPass.text = "Password: $pass" // Consider not displaying the password for better security
    }
}


