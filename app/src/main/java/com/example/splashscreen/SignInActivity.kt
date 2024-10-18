package com.example.splashscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    companion object {
        const val KEY1 = "com.example.splashscreen.SignInActivity.name"
        const val KEY2 = "com.example.splashscreen.SignInActivity.email" // Updated for email
        const val KEY3 = "com.example.splashscreen.SignInActivity.pass"  // Corrected for password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signInButton = findViewById<Button>(R.id.btnSignIn)
        val userEmail = findViewById<TextInputEditText>(R.id.etMail)
        val userPass = findViewById<TextInputEditText>(R.id.etPass)

        signInButton.setOnClickListener {
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()

            // Validation for empty fields
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            readData(email, pass)
        }
    }

    private fun readData(email: String, pass: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val sanitizedEmail = email.replace(".", ",")

        // Search user by email
        databaseReference.child(sanitizedEmail).get().addOnSuccessListener {
            if (it.exists()) {
                val savedPass = it.child("password").value.toString()

                // Check if password matches
                if (savedPass == pass) {
                    val name = it.child("name").value.toString()

                    val intentHome = Intent(this, HomeActivity::class.java)
                    intentHome.putExtra(KEY1, name)  // Pass user name
                    intentHome.putExtra(KEY2, email) // Pass user email
                    intentHome.putExtra(KEY3, pass)  // Pass user password
                    startActivity(intentHome)

                } else {
                    Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User does not exist. Please sign up.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error occurred. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }
}

