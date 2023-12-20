package com.example.bimbam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.example.bimbam.databinding.ActivityMainLoginBinding
import com.example.bimbam.databinding.ActivityMainRegistrationAccBinding
import com.example.bimbam.databinding.ActivityMainRegistrationChildBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity_Login : AppCompatActivity() {
    private lateinit var binding: ActivityMainLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val RelativeLayout = findViewById<RelativeLayout>(R.id.button)
        RelativeLayout.setOnClickListener {
            val intent = Intent(this@MainActivity_Login,MainActivity_homePage::class.java)
            startActivity(intent)
        }
    }
}